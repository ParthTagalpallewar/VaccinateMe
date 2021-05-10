package com.cowin.vaccinateme.data.models

data class Session(
        val session_id: String,
        val date: String,
        val available_capacity: String,
        val min_age_limit: String,
        val vaccine: String,
        val slots: ArrayList<String>,
)


