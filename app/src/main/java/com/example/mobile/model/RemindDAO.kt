package com.example.mobile.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RemindDAO {

    @Query("""
        SELECT reminder_table.* FROM reminder_table
        INNER JOIN categories ON reminder_table.creator_id = categories.id
        WHERE creator_id = :categoryId
    """
    )

    abstract fun ReminderFromCategory(categoryId: Long): Flow<List<ReminderToCategory>>

    @Query("""SELECT * FROM reminder_table WHERE id = :creation_time""")
    abstract fun reminder(creation_time: Long): reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: reminder):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: reminder)

    @Delete
    abstract suspend fun delete(entity: reminder): Int

}