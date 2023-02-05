package com.example.mobile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text


private val editor: SharedPreferences.Editor? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signUp = findViewById<TextView>(R.id.signUp)
        val logIn = findViewById<TextView>(R.id.logIn)
        val sign = findViewById<LinearLayout>(R.id.sign)
        val signIn = findViewById<Button>(R.id.signIn)
        val logInLayout = findViewById<LinearLayout>(R.id.logInLayout)
        signUp.setOnClickListener {
            signUp.background = resources.getDrawable(R.drawable.switch_trcks, null)
            signUp.setTextColor(resources.getColor(R.color.textColor, null))
            logIn.background = null
            sign.visibility = View.VISIBLE
            logInLayout.visibility = View.GONE
            logIn.setTextColor(resources.getColor(R.color.pinkColor, null))

        }
        logIn.setOnClickListener {
            signUp.background = null
            signUp.setTextColor(resources.getColor(R.color.pinkColor, null))
            logIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
            sign.visibility = View.GONE
            logInLayout.visibility = View.VISIBLE
            logIn.setTextColor(resources.getColor(R.color.white, null))
        }

        signIn.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)

        }}
}





