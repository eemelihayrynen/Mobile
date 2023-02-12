package com.example.mobile.model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import com.example.mobile.model.ReminderDao
import com.example.mobile.model.reminder


class ReminderRepository(private val reminderDao: ReminderDao) {
    fun readAllData(): Flow<List<reminder>> = reminderDao.readAllData()

    suspend fun addReminder(Reminder: reminder){
        reminderDao.addReminder(Reminder = Reminder)
    }

    suspend fun updateReminder(Reminder: reminder){
        reminderDao.updateReminder(Reminder = Reminder)
    }

    suspend fun deleteReminder(Reminder: reminder){
        reminderDao.deleteReminder(Reminder = Reminder)
    }

}