package com.example.mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



class MainActivity3 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
                                context.startActivity(Intent(context,Popup::class.java))
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
                content = {innerPadding ->
                    LazyColumn(
                        contentPadding = innerPadding,
                        modifier = Modifier.fillMaxSize()

                        ) {

                            items(20) {
                                Text(
                                    text = "Reminder $it",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 12.dp)
                                )

                                Button(
                                    onClick = {
                                        //val intent = Intent(this, popupmenu::class.java)
                                        //startActivity(intent)
                                        //finish()
                                    }
                                ) {
                                    Text(
                                        text = "Edit",
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Button(
                                    onClick = {
                                        //List.remove(item)
                                    },
                                ) {
                                    Text(
                                        text = "Delete",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                     })
    }}}
