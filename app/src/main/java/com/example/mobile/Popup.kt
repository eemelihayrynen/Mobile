package com.example.mobile

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.compose.foundation.lazy.layout.getDefaultLazyLayoutKey
import com.example.mobile.R



class Popup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        view.setOnClickListener {
            val dialog = TimePickerDialog(
                context,
                { view, hourOfDay, minute -> TODO("Not yet implemented") },
                hourOfDay,
                minute,
                false
            )
            dialog.show()
        }
    }}
