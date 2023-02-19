package com.example.mobile.repository

import androidx.lifecycle.LiveData
import com.example.mobile.model.Category
import kotlinx.coroutines.flow.Flow
import com.example.mobile.model.ReminderDao
import com.example.mobile.model.reminder


class ReminderRepository(private val reminderDao: ReminderDao) {
    fun categories(): Flow<List<Category>> = reminderDao.categories()

    fun getcategorywithID(categoryId: Long): Category? = reminderDao.getCategoryWithId(categoryId)

    suspend fun addCategory(category: Category): Long {
        return when (val local = reminderDao.getCategoryWithName(category.name)){
            null -> reminderDao.insert(category)
            else -> local.id
        }
    }
}
