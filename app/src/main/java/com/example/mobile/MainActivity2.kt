package com.example.mobile

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        setContentView(R.layout.activity_main2)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val usern2: String? = sharedPreferences.getString("username", "a")
    }

}