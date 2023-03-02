package com.example.mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.mobile.ui.theme.MobileTheme
import java.util.*

class MainActivity5 : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

    val extras = intent.extras
    val newstring = extras?.getString("message")
    val hours = extras?.getString("hour").toString()
    val min = extras?.getString("min").toString()
    Log.d("message: ", newstring.toString())
    Log.d("mins: ", min)
    Log.d("hours: ", hours)
        super.onCreate(savedInstanceState)

        setContent {
            MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Remind2(hour=hours.toInt(), min =min.toInt() , message = newstring.toString())
                }
            }
        }
    }

}