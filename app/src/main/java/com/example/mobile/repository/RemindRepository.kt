package com.example.mobile.repository

import com.example.mobile.model.RemindDAO
import com.example.mobile.model.ReminderToCategory
import com.example.mobile.model.reminder
import kotlinx.coroutines.flow.Flow

class RemindRepository (
    private val remindDAO:RemindDAO
){
    fun remindersInCategory(ReminderId: Long) : Flow<List<ReminderToCategory>> {
        return remindDAO.ReminderFromCategory(ReminderId)
    }

    suspend fun addReminder(Reminder: reminder) = remindDAO.insert(Reminder)

    suspend fun deleteReminder(reminder: reminder) = remindDAO.delete(reminder)
}