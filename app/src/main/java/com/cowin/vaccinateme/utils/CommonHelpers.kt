package com.cowin.vaccinateme.utils


import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.*

inline fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    return formatter.format(Calendar.getInstance().time)
}

fun <T> Context.move(activity:Class<T>,withFlags:Boolean = false){
    val intent = Intent(this,activity)
    if (withFlags){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    this.startActivity(intent)
}