package com.cowin.govaccine.data.repositionries

import android.content.Context
import com.cowin.govaccine.data.models.SettingsModel
import com.cowin.govaccine.data.network.NetworkInterface
import com.cowin.govaccine.data.roomdb.AppDatabase
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