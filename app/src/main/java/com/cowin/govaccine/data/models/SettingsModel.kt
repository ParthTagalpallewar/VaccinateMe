package com.cowin.govaccine.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SettingsModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val pincode:String,
    val date:String,
)