package com.cowin.vaccinateme.data.repositionries

import android.content.Context
import com.cowin.vaccinateme.data.models.roomModels.RoomCenters
import com.cowin.vaccinateme.data.models.roomModels.RoomSessions
import com.cowin.vaccinateme.data.roomdb.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CentersRepositiory(val context: Context) {

    suspend fun addListOfCenters(centers: ArrayList<RoomCenters>) = withContext(Dispatchers.IO) {

        AppDatabase.invoke(context).getCentersDao().insertCenters(centers.toList())

    }

    suspend fun addListOfSession(sessions : ArrayList<RoomSessions>) = withContext(Dispatchers.IO) {

        AppDatabase.invoke(context).getSessionDao().insertSessions(sessions.toList())

    }

}