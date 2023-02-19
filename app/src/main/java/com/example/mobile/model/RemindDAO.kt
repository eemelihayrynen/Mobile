package com.example.mobile.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RemindDAO {

    @Query("""
        SELECT reminders.* FROM reminders
        INNER JOIN categories ON reminders.creator_id = categories.id
        WHERE creator_id = :categoryId
    """
    )

    abstract fun ReminderFromCategory(categoryId: Long): Flow<List<ReminderToCategory>>

    @Query("""SELECT * FROM reminders WHERE id = :reminderId""")
    abstract fun reminder(reminderId: Long): reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: reminder):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: reminder)

    @Delete
    abstract suspend fun delete(entity: reminder): Int

}