package com.example.mobile.model

data class reminder (
    val Message: String,
    val location_x: String,
    val location_y: String,
    val reminder_time : String,
    val creation_time: String,
    val creator_id: String,
    val reminder_seen: String
)