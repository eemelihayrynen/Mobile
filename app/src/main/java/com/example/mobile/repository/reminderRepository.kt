package com.example.mobile.repository

import com.example.mobile.model.reminder

class reminderRepository {
    fun getAllData(): List<reminder>{
        return listOf(
            reminder(
                Message = "Wakeup",
                location_x= "Wakeup",
                location_y= "Wakeup",
                reminder_time = "4:10",
                creation_time= "5",
                creator_id= "Wakeup",
                reminder_seen= "Wakeup"
            )
        )
    }
}