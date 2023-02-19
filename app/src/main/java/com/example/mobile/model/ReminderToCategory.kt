package com.example.mobile.model

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import java.util.*

class ReminderToCategory {
    @Embedded
    lateinit var Reminder: reminder

    @Relation(parentColumn = "creator-id", entityColumn = "id")
    lateinit var _categories: List<Category>

    @get:Ignore
    val category: Category
        get()=_categories[0]


    operator fun component1()=Reminder
    operator fun component2()=category

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is ReminderToCategory -> Reminder == other.Reminder && _categories == other._categories
        else -> false
    }

    override fun hashCode(): Int = Objects.hash(Reminder, _categories)
    }

