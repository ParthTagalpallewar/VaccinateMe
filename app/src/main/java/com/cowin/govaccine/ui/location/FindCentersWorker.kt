package com.cowin.govaccine.ui.location

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cowin.govaccine.R
import com.cowin.govaccine.data.models.CowinCentersResponse
import com.cowin.govaccine.data.models.roomModels.RoomAppointmentsModel
import com.cowin.govaccine.data.models.roomModels.RoomCenters
import com.cowin.govaccine.data.models.roomModels.RoomSessions
import com.cowin.govaccine.data.repositionries.CentersRepositiory
import com.cowin.govaccine.data.repositionries.UserDataRepositories
import com.cowin.govaccine.ui.main.MainActivity
import com.cowin.govaccine.utils.getAppointsModel
import com.cowin.govaccine.utils.getCurrentDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response


class FindCentersWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    lateinit var userRepo: UserDataRepositories
    lateinit var centersRepositories: CentersRepositiory
    lateinit var centersInRoomDatabase: List<RoomCenters>
    lateinit var sessionsInRoomDatabase: List<RoomSessions>

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        return try {
            work()
            Result.Success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun work() {

        GlobalScope.launch {
            userRepo = UserDataRepositories(applicationContext)
            centersRepositories = CentersRepositiory(applicationContext)
            centersInRoomDatabase = centersRepositories.getAllCenters()
            sessionsInRoomDatabase = centersRepositories.getAllSessions()


            userRepo.getUserData()?.apply {

                //getting latest Appointment
                val latestAppointments = userRepo.getAppointments(pincode, getCurrentDate())
                logAppointments(latestAppointments)

                if (latestAppointments.isSuccessful) {

                    val latestAppointmentModel = latestAppointments.body()?.getAppointsModel()

                    if (centersInRoomDatabase.isEmpty()) {
                        //Add Data inside room
                        val notify = checkToNotify(latestAppointments)
                        saveAppointmentsInDatabase(latestAppointments)

                        if (notify) {
                            sendNotification()
                        }

                    } else {

                        val notify = checkToNotify(latestAppointments)
                        if (latestAppointmentModel != null) {
                            //if (checkUpdateNeeded(latestAppointmentModel, sessionsInRoomDatabase)) {
                                saveAppointmentsInDatabase(latestAppointments)
                            //}
                        }

                        if (notify) {
                            sendNotification()
                        }
                    }


                }
            }
        }


    }

    private fun checkUpdateNeeded(
        latestAppointmentModel: RoomAppointmentsModel,
        sessionsInRoomDatabase: List<RoomSessions>
    ): Boolean {
        for (networkSession in latestAppointmentModel.sessionsList) {
            val roomSession = getRoomSession(networkSession, sessionsInRoomDatabase)

            if (roomSession != null) {
                roomSession.available_capacity < networkSession.available_capacity
                return true
            }
        }

        return false
    }

    private fun getRoomSession(
        networkSession: RoomSessions,
        sessionsInRoomDatabase: List<RoomSessions>
    ): RoomSessions? {
        for (sessions in sessionsInRoomDatabase) {
            if ((sessions.center_id == networkSession.center_id) and (sessions.date == networkSession.date)) {
                return sessions
            }
        }
        return null
    }

    private fun logAppointments(latestAppointments: Response<CowinCentersResponse>) {
        val data = latestAppointments.body()?.centers

        if (data != null) {
            for (centers in data) {

                Log.e("LOG", "Center ID ${centers.center_id}, name ${centers.name}, sessions ${centers.sessions.size}")
                for (session in centers.sessions) {
                    Log.e("LOG", "Session ID ${session.session_id}, date ${session.date}, available ${session.available_capacity}")
                }
            }
        }
    }

    private fun sendNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("channelId", "description", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.apply {
                lightColor = applicationContext.resources.getColor(R.color.colorPrimary)
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(notificationChannel)

            val builder = Notification.Builder(applicationContext, "channelId")
            builder.apply {
                setContentTitle("Vaccines Available!")
                setContentText("New slots available for booking, Click To Check")
                setSmallIcon(R.drawable.logo2)
                setLargeIcon(
                    BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.logo2
                    )
                )
                setContentIntent(pendingIntent)
            }

            notificationManager.notify(123, builder.build())

        }

    }

    private fun alreadySaved(newSession: RoomSessions): Boolean {
        for (dbSession in sessionsInRoomDatabase) {
            if (dbSession.session_id == newSession.session_id) {
                //Log.e("TAG", "DEBUG: >>>>>>>>>>>>> ${i.available_capacity} ${session.available_capacity} ${session.session_id}")
                if (dbSession.available_capacity == "0") {
                    Log.e("NOTIFY", "${newSession.session_id} Capacity increased from zero to ${newSession.available_capacity}  treat as new")
                    return false
                } else {
                    return true
                }
            }
        }
        return false
    }

    private suspend fun checkToNotify(response: Response<CowinCentersResponse>): Boolean {
        val sessions = response.body()?.getAppointsModel()?.sessionsList

        if (sessions != null) {
            for (session in sessions) {
                if (session.available_capacity.toInt() == 0) {
                    continue
                }

                if (alreadySaved(session)) {
                    continue
                }
                Log.e("NOTIFY", "${session.session_id} New session with capacity = ${session.available_capacity}, notify = true")
                return true
            }

            return false
        } else {
            return false
        }
    }

    private suspend fun saveAppointmentsInDatabase(
        response: Response<CowinCentersResponse>) {

        response.body()?.getAppointsModel()?.apply {
            centersRepositories.apply {
                deleteAllData()
                addListOfCenters(centersList)
                addListOfSession(sessionsList)
            }
        }
    }
}
