package com.example.mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.mobile.model.Remind
import com.example.mobile.ui.theme.MobileTheme
import java.util.*

class MainActivity8 : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        val extras = intent.extras
        val location = extras?.getString("location")
        Log.d("location: ", location.toString())
        super.onCreate(savedInstanceState)

        setContent {
            MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                }
            }
        }
    }

}