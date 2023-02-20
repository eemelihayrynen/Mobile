package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.TimePickerDialog
import android.content.Intent
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import com.example.mobile.model.ReminderViewModel
import com.example.mobile.model.reminder
import com.example.mobile.ui.theme.MobileTheme
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*



class MainActivity4 : AppCompatActivity() {
    private lateinit var mViewModel: ReminderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MobileComputingApp2()
                }
            }
        }
    }}
