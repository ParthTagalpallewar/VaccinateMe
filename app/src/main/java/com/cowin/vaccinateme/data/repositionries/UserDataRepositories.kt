package com.cowin.vaccinateme.data.repositionries

import android.content.Context
import android.service.autofill.UserData
import com.cowin.vaccinateme.data.models.CowinCentersResponse
import com.cowin.vaccinateme.data.models.SettingsModel
import com.cowin.vaccinateme.data.network.NetworkInterface
import com.cowin.vaccinateme.data.roomdb.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDataRepositories(val context: Context) {
    suspend fun getUserData() =
        withContext(Dispatchers.IO) { AppDatabase.invoke(context).getUserDao().getSettings() }


    suspend fun addData(user: SettingsModel) = withContext(Dispatchers.IO)
    { AppDatabase.invoke(context).getUserDao().createSettings(user) }

    suspend fun UpdateData(user: SettingsModel) =
        withContext(Dispatchers.IO) {
            AppDatabase.invoke(context).getUserDao().updateSettings(user)
        }

    suspend fun getAppointments(pincode:String,date:String) = withContext(Dispatchers.IO){
        NetworkInterface.invoke().getAppointments(pincode, date)
    }


}