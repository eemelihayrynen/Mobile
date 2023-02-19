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
        setContentView(R.layout.activity_main3)
        val mPickTimeBtn = findViewById<Button>(R.id.pickTimeBtn)
        val textView     = findViewById<TextView>(R.id.timeTv)
        val ok = findViewById<Button>(R.id.ok)
        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        ok.setOnClickListener{
            insertDataToDatabase()
            setContent {
                MobileTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        MobileComputingApp()
                    }
                }
            }
        }
    }

    private fun insertDataToDatabase(){
        val time = findViewById<TextView>(R.id.timeTv).text.toString()
        val name = findViewById<TextInputEditText>(R.id.remindertext).text.toString()

        val remind = reminder(5,5,"test","time","time","5","test")


    }
    private fun inputCheck(time: String, name: String): Boolean{
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(time))
    }
        }
