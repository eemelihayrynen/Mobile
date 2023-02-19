package com.example.mobile

import android.content.Context
import androidx.room.Room
import com.example.mobile.model.ReminderDatabase
import com.example.mobile.repository.RemindRepository
import com.example.mobile.repository.ReminderRepository

object Graph {
    lateinit var database: ReminderDatabase
        private set

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    val remindRepository by lazy {
        RemindRepository(
            remindDAO = database.remindDAO()
        )
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, ReminderDatabase::class.java, "mcData.db")
            .fallbackToDestructiveMigration() // don't use this in production app
            .build()
    }
}
