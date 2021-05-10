package com.cowin.vaccinateme.data.models.roomModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomCenters (

        @PrimaryKey(autoGenerate = true)
        val centerRoomId:Int,

        val center_id:String,

        val name:String,

        val sessionCounts:Int

        )