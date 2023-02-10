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
            ) ,
            reminder(
            Message = "Meeting",
            location_x= "Wakeup",
            location_y= "Wakeup",
            reminder_time = "7.00",
            creation_time= "6",
            creator_id= "Wakeup",
            reminder_seen= "Wakeup"
        ),
            reminder(
                Message = "Meeting",
                location_x= "Wakeup",
                location_y= "Wakeup",
                reminder_time = "7.00",
                creation_time= "7",
                creator_id= "Wakeup",
                reminder_seen= "Wakeup"
            )
            ,
            reminder(
                Message = "Meeting",
                location_x= "Wakeup",
                location_y= "Wakeup",
                reminder_time = "7.00",
                creation_time= "8",
                creator_id= "Wakeup",
                reminder_seen= "Wakeup"
            ),
            reminder(
                Message = "Meeting",
                location_x= "Wakeup",
                location_y= "Wakeup",
                reminder_time = "7.00",
                creation_time= "9",
                creator_id= "Wakeup",
                reminder_seen= "Wakeup"
            ),
            reminder(
                Message = "Meeting",
                location_x= "Wakeup",
                location_y= "Wakeup",
                reminder_time = "7.00",
                creation_time= "11",
                creator_id= "Wakeup",
                reminder_seen= "Wakeup"
            ),
            reminder(
                Message = "Meeting",
                location_x= "Wakeup",
                location_y= "Wakeup",
                reminder_time = "7.00",
                creation_time= "12",
                creator_id= "Wakeup",
                reminder_seen= "Wakeup"
            ),
            reminder(
                Message = "Meeting",
                location_x= "Wakeup",
                location_y= "Wakeup",
                reminder_time = "7.00",
                creation_time= "13",
                creator_id= "Wakeup",
                reminder_seen= "Wakeup"
            ),
            reminder(
                Message = "Meeting",
                location_x= "Wakeup",
                location_y= "Wakeup",
                reminder_time = "7.00",
                creation_time= "14",
                creator_id= "Wakeup",
                reminder_seen= "Wakeup"
            )
        )
    }
}