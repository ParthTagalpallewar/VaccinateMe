package com.cowin.vaccinateme.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cowin.vaccinateme.data.models.Centers
import com.cowin.vaccinateme.data.models.roomModels.RoomCenters
import com.cowin.vaccinateme.data.models.roomModels.RoomSessions
import com.cowin.vaccinateme.data.repositionries.CentersRepositiory
import com.cowin.vaccinateme.data.repositionries.UserDataRepositories
import com.cowin.vaccinateme.data.roomdb.AppDatabase
import com.cowin.vaccinateme.utils.getCurrentDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

           userRepo.getUserData().apply {
               if(this != null){

                   //getting user appointments using pincode and currentDate
                   val response = userRepo.getAppointments(pincode, getCurrentDate())
                   val responseBody  = response.body()


                   val roomDataBaseCenterList:ArrayList<RoomCenters> =  ArrayList<RoomCenters>()
                   val roomDataBaseSessionsList:ArrayList<RoomSessions> =  ArrayList<RoomSessions>()

                   if (responseBody!= null){
                       for(center in responseBody.centers){

                           val sessionArray = center.sessions
                           for (session in sessionArray){
                               session.apply {
                                   val sessionModel = RoomSessions(0,center.center_id,session_id,date, available_capacity, min_age_limit, vaccine)
                                   roomDataBaseSessionsList.add(sessionModel)
                               }

                           }


                         val centerModel =   RoomCenters(0,center.center_id,center.name,center.sessions.size)
                         roomDataBaseCenterList.add(centerModel)
                       }
                   }

                   centersRepositories.addListOfCenters(roomDataBaseCenterList)
                   centersRepositories.addListOfSession(roomDataBaseSessionsList)
               }
           }


        }
    }
}
