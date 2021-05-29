package com.cowin.govaccine.utils


import android.content.Context
import android.content.Intent
import com.cowin.govaccine.data.models.Centers
import com.cowin.govaccine.data.models.CowinCentersResponse
import com.cowin.govaccine.data.models.Session
import com.cowin.govaccine.data.models.roomModels.RoomAppointmentsModel
import com.cowin.govaccine.data.models.roomModels.RoomCenters
import com.cowin.govaccine.data.models.roomModels.RoomSessions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

inline fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    return formatter.format(Calendar.getInstance().time)
}

fun <T> Context.move(activity: Class<T>, withFlags: Boolean = false) {
    val intent = Intent(this, activity)
    if (withFlags) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    this.startActivity(intent)
}


suspend fun CowinCentersResponse.getAppointsModel()  = withContext(Dispatchers.Default) {
        val roomDataBaseCenterList: ArrayList<RoomCenters> = ArrayList<RoomCenters>()
        val roomDataBaseSessionsList: ArrayList<RoomSessions> = ArrayList<RoomSessions>()

        //Iteration in centers
        for (center in centers) {

            //Iteration in sessions
            for (session in center.sessions) {
                roomDataBaseSessionsList.add(session + center.center_id)
            }

            roomDataBaseCenterList.add(center.getRoomCenter())
        }

       RoomAppointmentsModel(roomDataBaseCenterList,roomDataBaseSessionsList)
    }


operator fun Session.plus(centerId: String): RoomSessions {
    return RoomSessions(session_id, centerId, date, available_capacity, min_age_limit, vaccine)
}

fun Centers.getRoomCenter():RoomCenters{
    var num_slots: Int = 0
    for (s in sessions) {
        num_slots += s.available_capacity.toInt()
    }
    return RoomCenters(center_id,name,sessions.size, num_slots)
}
