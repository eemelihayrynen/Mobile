package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import java.time.Instant



class MainActivity3 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainContent()

            /*LazyColumn(modifier = Modifier.fillMaxSize()
                , contentPadding = PaddingValues(top = 0.dp)
            ) {

                items(20) {
                    Text(
                        text = "Reminder $it",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(PaddingValues(top = 56.dp))
                            .padding(vertical = 12.dp)
                            .padding(top = 0.dp)
                    )
                    Button(
                        onClick = { }
                    ) {
                        Text(text = "Show more",
                        textAlign = TextAlign.Center)
                    }
                }
            }*/
        }

    }}
        @Composable
        fun MainContent() {

            // Create a boolean variable
            // to store the display menu state
            var mDisplayMenu by remember { mutableStateOf(false) }

            // fetching local context
            val mContext = LocalContext.current

            // Creating a Top bar
            Scaffold(
                topBar = {
                TopAppBar(
                    title = { Text("Home", color = Color.White) },
                    backgroundColor = Color(0xFF001AFF),
                    actions = {

                        // Creating Icon button favorites, on click
                        // would create a Toast message
                        IconButton(onClick = {
                            Toast.makeText(mContext, "New", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.Add, "")
                        }

                        // Creating Icon button for dropdown menu
                        IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                            Icon(Icons.Default.MoreVert, "")
                        }

                        // Creating a dropdown menu
                        DropdownMenu(
                            expanded = mDisplayMenu,
                            onDismissRequest = { mDisplayMenu = false }
                        ) {

                            // Creating dropdown menu item, on click
                            // would create a Toast message
                            val context = LocalContext.current
                            DropdownMenuItem(onClick = {
                                context.startActivity(Intent(context,MainActivity2::class.java))
                                Toast.makeText(
                                    mContext,
                                    "Settings",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Text(text = "Profile settings")
                            }

                            // Creating dropdown menu item, on click
                            // would create a Toast message
                            DropdownMenuItem(onClick = {
                                context.startActivity(Intent(context,MainActivity::class.java))
                                Toast.makeText(
                                    mContext,
                                    "Logout",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Text(text = "Logout")
                            }
                        }
                }
            )
        }) {innerPadding ->


            }

        }

        // For displaying preview in
// the Android Studio IDE emulator
        @Preview(showBackground = true)
        @Composable
        fun DefaultPreview() {
            MainContent()
        }


