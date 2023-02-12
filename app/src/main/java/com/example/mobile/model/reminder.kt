package com.example.mobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "reminder_table")
data class reminder (
    @PrimaryKey(autoGenerate = true)
    val creator_id: Int,
    val Message: String,
    val location_x: String,
    val location_y: String,
    val reminder_time : String,
    val creation_time: String,
    val reminder_seen: String
)