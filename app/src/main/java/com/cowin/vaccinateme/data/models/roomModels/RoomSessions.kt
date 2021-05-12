package com.cowin.vaccinateme.data.models.roomModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomSessions(

        @PrimaryKey(autoGenerate = false)
        val session_id: String,

        val center_id:String,
        val date: String,
        val available_capacity: String,
        val min_age_limit: String,
        val vaccine: String,

)