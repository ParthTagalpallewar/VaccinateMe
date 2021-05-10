package com.cowin.vaccinateme.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
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

                   if (responseBody!= null){

                       val centersSize = responseBody.centers.size
                       for(centers in responseBody.centers){

                       }
                   }


               }
           }


        }
    }
}
