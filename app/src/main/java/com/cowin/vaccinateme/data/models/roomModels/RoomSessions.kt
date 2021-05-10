package com.cowin.vaccinateme.data.models.roomModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomSessions(

        @PrimaryKey(autoGenerate = true)
        val sessionsRoomId:Int,

        val center_id:String,

        val session_id: String,
        val date: String,
        val available_capacity: String,
        val min_age_limit: String,
        val vaccine: String,

)