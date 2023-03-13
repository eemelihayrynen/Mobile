package com.example.mobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater

import android.view.View
import android.widget.*
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.mobile.ui.theme.MobileTheme
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text
import androidx.compose.material.Surface


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
        val user1 = findViewById<TextInputEditText>(R.id.emails)
        val pass1 = findViewById<TextInputEditText>(R.id.passwords1)
        val user2 = findViewById<TextInputEditText>(R.id.email)
        val pass2 = findViewById<TextInputEditText>(R.id.passwords)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var logOrSign = true // true is log in and false is sign up
        signUp.setOnClickListener {
            logOrSign = false
            signUp.background = resources.getDrawable(R.drawable.switch_trcks, null)
            signUp.setTextColor(resources.getColor(R.color.textColor, null))
            logIn.background = null
            sign.visibility = View.VISIBLE
            logInLayout.visibility = View.GONE
            logIn.setTextColor(resources.getColor(R.color.pinkColor, null))
        }
        logIn.setOnClickListener {
            logOrSign = true
            signUp.background = null
            signUp.setTextColor(resources.getColor(R.color.pinkColor, null))
            logIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
            sign.visibility = View.GONE
            logInLayout.visibility = View.VISIBLE
            logIn.setTextColor(resources.getColor(R.color.white, null))
        }

        signIn.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val passw: String? = sharedPreferences.getString("password", "a")
            val usern: String? = sharedPreferences.getString("username", "a")
            if (usern == "a" && passw == "a" && logOrSign == true){
                Toast.makeText(applicationContext,"Sign up first",Toast.LENGTH_SHORT).show()
            }else if(logOrSign == false){
                editor.putString("password", pass1.text.toString().trim())
                editor.putString("username", user1.text.toString().trim())
                editor.putInt("id", 1)
                editor.apply()
                Toast.makeText(applicationContext,"Email & Password saved, Welcome "+ user1.text.toString().trim(),Toast.LENGTH_SHORT).show()
                setContent {
                    MobileTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colors.background) {
                            Home()
                        }
                    }
                }
            }
            val passw2: String? = sharedPreferences.getString("password", "a")
            val usern2: String? = sharedPreferences.getString("username", "a")
            val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
            if (passw2 == pass2.text.toString() && usern2 == user2.text.toString()){
                Toast.makeText(applicationContext,"Welcome "+usern2,Toast.LENGTH_SHORT).show()
                setContent {
                    MobileTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colors.background) {
                            MobileComputingApp()
                        }
                    }
                }
            }else if(usern != ""&&(passw2 != pass1.text.toString() || usern2 != user1.text.toString())){
                Toast.makeText(applicationContext,"Incorrect email or password",Toast.LENGTH_SHORT).show()
            }


        }}
}





