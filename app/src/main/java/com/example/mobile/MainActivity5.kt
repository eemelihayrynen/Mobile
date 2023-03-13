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
    Log.d("moi: ", "moi2")
    val extras = intent.extras
    val newstring = extras?.getString("message")
    val hours = extras?.getString("hour").toString()
    val min = extras?.getString("min").toString()
    super.onCreate(savedInstanceState)
    try{
        val locationy = extras?.getString("locationy").toString()
        val locationx = extras?.getString("locationx").toString()
        Log.d("moi: ", "moi3")
        setContent {
            MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Remind3(hour=hours.toInt(), min =min.toInt() , message = newstring.toString(),locationy = locationy,locationx=locationx)
                }
            }
        }
    } catch (e: Exception) {
        setContent {
            MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Remind2(hour=hours.toInt(), min =min.toInt() , message = newstring.toString())
                }}}}

        setContent {
            MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Remind2(hour=hours.toInt(), min =min.toInt() , message = newstring.toString())
                }}}}}
