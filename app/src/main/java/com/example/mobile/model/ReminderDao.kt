package com.example.mobile.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.mobile.model.Category


@Dao
abstract class ReminderDao {
    @Query(value = "SELECT * FROM categories WHERE name = :name")
    abstract suspend fun getCategoryWithName(name: String): Category?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Category):Long

    @Query(value = "SELECT * FROM categories WHERE id = :categoryId")
    abstract fun getCategoryWithId(categoryId: Long): Category?

    @Query(value = "SELECT * FROM categories LIMIT 15")
    abstract fun categories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entity: Collection<Category>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Category)

    @Delete
    abstract suspend fun delete(entity: Category): Int



}