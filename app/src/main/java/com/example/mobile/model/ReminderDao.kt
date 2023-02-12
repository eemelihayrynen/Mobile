package com.example.mobile.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ReminderDao {

    @Insert
    suspend fun addReminder(Reminder: reminder)

    @Update
    suspend fun updateReminder(Reminder: reminder)

    @Delete
    suspend fun deleteReminder(Reminder: reminder)


    @Query("SELECT * FROM reminder_table")
    fun readAllData(): Flow<List<reminder>>
}