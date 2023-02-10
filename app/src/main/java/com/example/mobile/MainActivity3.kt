package com.example.mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import com.example.mobile.repository.reminderRepository
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.example.mobile.ui.theme.MobileTheme

class MainActivity3 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val remRep = reminderRepository()
            val getAllData = remRep.getAllData()

            MobileTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(

                            title = {
                                Text(
                                    "Home",
                                    maxLines = 1,
                                    overflow = TextOverflow.Visible
                                )
                            },
                            navigationIcon = {
                                val context = LocalContext.current
                                IconButton(onClick = {context.startActivity(Intent(context,MainActivity::class.java))}) {

                                    Icon(
                                        imageVector = Icons.Filled.ExitToApp,
                                        contentDescription = "Log Out"
                                    )
                                }
                            },
                            actions = {
                                val context = LocalContext.current
                                IconButton(onClick = {
                                    context.startActivity(Intent(context,MainActivity4::class.java))
                                    finish()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Add"
                                    )
                                }
                                IconButton(onClick = {
                                    context.startActivity(Intent(context,MainActivity2::class.java))}) {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = "Profile"
                                    )
                                }
                            },
                            )
                    },
                )
                { innerPadding ->
                LazyColumn(contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(12.dp))
                {
                    itemsIndexed(
                        items = getAllData,
                        key = {
                            index, Reminder ->
                            Reminder.creation_time
                        }
                    ){
                        index,Reminder ->
                        com.example.mobile.item(Reminder = Reminder)
                    }
                }
                }
            }
            }
    }
}
