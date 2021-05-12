package com.cowin.vaccinateme.ui.location

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.models.CowinCentersResponse
import com.cowin.vaccinateme.data.models.roomModels.RoomAppointmentsModel
import com.cowin.vaccinateme.data.models.roomModels.RoomSessions
import com.cowin.vaccinateme.data.repositionries.CentersRepositiory
import com.cowin.vaccinateme.data.repositionries.UserDataRepositories
import com.cowin.vaccinateme.ui.main.MainActivity
import com.cowin.vaccinateme.utils.getAppointsModel
import com.cowin.vaccinateme.utils.getCurrentDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class FindCentersWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {


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
            val userRepo = UserDataRepositories(applicationContext)
            val centersRepositories = CentersRepositiory(applicationContext)

            userRepo.getUserData()?.apply {

                //getting latest Appointment
                val latestAppointments = userRepo.getAppointments(pincode, getCurrentDate())
                logAppointments(latestAppointments)

                if (latestAppointments.isSuccessful) {
                    val latestAppointmentModel = latestAppointments.body()?.getAppointsModel()


                    //getting centers Available in room
                    val centersInRoomDatabase = centersRepositories.getAllCenters()
                    val sessionsInRoomDatabase = centersRepositories.getAllSessions()


                    //if new user or no data in room
                    if (centersInRoomDatabase.isEmpty()) {
                        //Add Data inside room
                        saveAppointmentsInDatabase(latestAppointments, centersRepositories)

                        val notify = checkToNotify(latestAppointments)
                        if (notify) {
                            sendNotification()
                        }

                    } else {

                        if (latestAppointmentModel != null) {
                            if (checkUpdateNeeded(latestAppointmentModel, sessionsInRoomDatabase)) {
                                saveAppointmentsInDatabase(latestAppointments, centersRepositories)
                            }
                        }

                        val notify = checkToNotify(latestAppointments)
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
                Log.e(
                    "centersLog", """logAppointments:
                    |
                    |Centers Name :-  ${centers.name} 
                    |Session Size : - ${centers.sessions.size}
                    |
                    | """"
                )
                for (session in centers.sessions) {
                    Log.e(
                        "centersLog", """logAppointments:
                    |
                    |Session ID :-  ${session.session_id} 
                    |Session Size : - ${session.date}
                    |
                    | """"
                    )
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
                setContentText("Looking Like Vaccines Are Available You, Click To Check")
                setSmallIcon(R.drawable.logo2)
                setLargeIcon(
                    BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.logo_big
                    )
                )
                setContentIntent(pendingIntent)
            }

            notificationManager.notify(123, builder.build())

        }

    }

    private suspend fun checkToNotify(response: Response<CowinCentersResponse>): Boolean {
        val sessions = response.body()?.getAppointsModel()?.sessionsList

        if (sessions != null) {
            for (session in sessions) {
                if (session.available_capacity.toInt() > 0) {
                    return true
                }
            }

            return false
        } else {
            return false
        }
    }

    private suspend fun saveAppointmentsInDatabase(
        response: Response<CowinCentersResponse>,
        centersRepositories: CentersRepositiory
    ) {
        response.body()?.getAppointsModel()?.apply {
            centersRepositories.apply {
                deleteAllData()
                addListOfCenters(centersList)
                addListOfSession(sessionsList)
            }
        }
    }
}
