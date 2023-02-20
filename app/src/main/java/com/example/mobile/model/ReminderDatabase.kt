package com.example.mobile.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Category::class, reminder::class], version = 2, exportSchema = false)
abstract class ReminderDatabase: RoomDatabase() {
    abstract fun reminderDao(): CategoryDao
    abstract fun remindDAO(): RemindDAO
}