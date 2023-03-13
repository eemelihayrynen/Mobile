package com.example.mobile.model

import android.content.Intent

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mobile.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule
val lista = mutableListOf("")
val nimi = mutableListOf("")
@Composable
fun Remind(
    onBackPress: () -> Unit,
    viewModel: RemindViewModel = viewModel(), // uus viewmodel
    navController: NavController
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val title = rememberSaveable { mutableStateOf("") }
    val amount = rememberSaveable { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val data = Data.Builder()

    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text(text = "Reminder name")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()){
                    OutlinedTextField(
                        value = amount.value,
                        onValueChange = { amount.value = it },
                        label = { Text(text = "time")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (latlng == null) {
                        OutlinedButton(
                            onClick = {
                                navController.navigate("map")
                                      },
                            modifier = Modifier.height(55.dp)
                        ) {
                            Text(text = "Reminder location")
                        }
                    }else {
                        Text(
                            text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Checkbox(
                        checked = isChecked.value,
                        onCheckedChange = { isChecked.value = it },
                        enabled = true,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(6.dp),
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Text(
                        "Notification",
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
                Log.d("ischecked: ", isChecked.value.toString())

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    enabled = true,
                    onClick = {
                        if (latlng == null) {
                            if (amount.value != "" && isChecked.value) {
                                data.putString("message", title.value)

                                val split = amount.value.split(".")
                                val hour = split[0]
                                val min = split[1]
                                val calendar: Calendar = Calendar.getInstance()
                                calendar.set(
                                    Calendar.getInstance().get(Calendar.YEAR),
                                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                                    hour.toInt(),
                                    min.toInt()
                                )
                                val goalTime = calendar.getTimeInMillis()
                                val nowTime = ampm()
                                val milseconds = goalTime - nowTime
                                val seconds = milseconds / 1000
                                Log.d("seconds: ", seconds.toString())
                                val workerRequest =
                                    OneTimeWorkRequestBuilder<NotificationRequestWorker>()
                                        .setInitialDelay(seconds, TimeUnit.SECONDS)
                                        .setInputData(data.build())
                                        .build()
                                // Enqueue the above workrequest object to the WorkManager
                                WorkManager.getInstance(context).enqueue(workerRequest)
                                context.startActivity(
                                    Intent(
                                        context,
                                        MainActivity6::class.java
                                    ).putExtra("Message", title.value)
                                )
                            }
                            if (amount.value != "" && !isChecked.value) {
                                data.putString("message", title.value)
                                val split = amount.value.split(".")
                                val hour = split[0]
                                val min = split[1]
                                val calendar: Calendar = Calendar.getInstance()
                                calendar.set(
                                    Calendar.getInstance().get(Calendar.YEAR),
                                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                                    hour.toInt(),
                                    min.toInt()
                                )
                                val goalTime = calendar.getTimeInMillis()
                                val nowTime = ampm()
                                val milseconds = goalTime - nowTime
                                val seconds = milseconds / 1000
                                Log.d("seconds: ", seconds.toString())
                                Timer().schedule(milseconds) {
                                    context.startActivity(
                                        Intent(
                                            context,
                                            MainActivity5::class.java
                                        ).putExtra("hour", hour).putExtra("message", title.value)
                                            .putExtra("min", min)
                                    )
                                }
                                context.startActivity(
                                    Intent(
                                        context,
                                        MainActivity6::class.java
                                    ).putExtra("Message", title.value)
                                )
                            }
                            if (amount.value == "") {
                                coroutineScope.launch {
                                    viewModel.saveRemind( // uus viewmodel
                                        reminder(
                                            creation_time = 0,
                                            creator_id = getCategoryId(
                                                viewState.categories,
                                                "Reminders"
                                            ),
                                            Message = title.value,
                                            location_x = "no location",
                                            location_y = "no location",
                                            reminder_time = "No time",
                                            reminder_seen = "reminder seen"
                                        )
                                    )

                                    context.startActivity(
                                        Intent(
                                            context,
                                            MainActivity6::class.java
                                        ).putExtra("Message", title.value)
                                    )
                                }
                            }
                        }else{
                            if (amount.value != "" && isChecked.value) {
                                data.putString("message", title.value)
                                data.putString("locationy",latlng.longitude.toString())
                                data.putString("locationx",latlng.latitude.toString())
                                Log.d("moi: ", "moi")
                                val split = amount.value.split(".")
                                val hour = split[0]
                                val min = split[1]
                                val calendar: Calendar = Calendar.getInstance()
                                calendar.set(
                                    Calendar.getInstance().get(Calendar.YEAR),
                                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                                    hour.toInt(),
                                    min.toInt()
                                )
                                val goalTime = calendar.getTimeInMillis()
                                val nowTime = ampm()
                                val milseconds = goalTime - nowTime
                                val seconds = milseconds / 1000
                                Log.d("seconds: ", seconds.toString())
                                list(latlng,title.value)

                                val workerRequest =
                                    OneTimeWorkRequestBuilder<NotificationRequestWorker>()
                                        .setInitialDelay(seconds, TimeUnit.SECONDS)
                                        .setInputData(data.build())
                                        .build()
                                // Enqueue the above workrequest object to the WorkManager
                                WorkManager.getInstance(context).enqueue(workerRequest)
                                context.startActivity(
                                    Intent(
                                        context,
                                        MainActivity6::class.java
                                    ).putExtra("Message", title.value)
                                )
                            }
                            if (amount.value != "" && !isChecked.value) {
                                data.putString("message", title.value)
                                val split = amount.value.split(".")
                                val hour = split[0]
                                val min = split[1]
                                val calendar: Calendar = Calendar.getInstance()
                                calendar.set(
                                    Calendar.getInstance().get(Calendar.YEAR),
                                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                                    hour.toInt(),
                                    min.toInt()
                                )
                                val goalTime = calendar.getTimeInMillis()
                                val nowTime = ampm()
                                val milseconds = goalTime - nowTime
                                val seconds = milseconds / 1000
                                Log.d("seconds: ", seconds.toString())
                                list(latlng,title.value)
                                Timer().schedule(milseconds) {
                                    context.startActivity(
                                        Intent(
                                            context,
                                            MainActivity5::class.java
                                        ).putExtra("hour", hour).putExtra("message", title.value).putExtra("locationx", latlng.latitude.toString()).putExtra("locationy",latlng.longitude.toString())
                                            .putExtra("min", min)
                                    )
                                }

                                context.startActivity(
                                    Intent(
                                        context,
                                        MainActivity6::class.java
                                    ).putExtra("Message", title.value)
                                )
                            }
                            if (amount.value == "" && !isChecked.value) {
                                /*TODO: if the user is close enough to the alarm it gets added*/
                                context.startActivity(Intent(context, MainActivity9::class.java))
                                Log.d("moro: ", "mororororo")
                                list(latlng,title.value)
                                coroutineScope.launch {
                                    viewModel.saveRemind( // uus viewmodel
                                        reminder(
                                            creation_time = 0,
                                            creator_id = getCategoryId(
                                                viewState.categories,
                                                "Reminders"
                                            ),
                                            Message = title.value,
                                            location_x = latlng.latitude.toString(),
                                            location_y = latlng.longitude.toString(),
                                            reminder_time = "No time",
                                            reminder_seen = "reminder seen"
                                        )
                                    )
                                    context.startActivity(
                                        Intent(
                                            context,
                                            MainActivity6::class.java
                                        ).putExtra("Message", title.value)
                                    )
                                }}
                            if (amount.value == "" && isChecked.value) {
                                    list(latlng,title.value)
                                    data.putString("message", title.value)
                                    data.putString("locationy",latlng.longitude.toString())
                                    data.putString("locationx",latlng.latitude.toString())
                                    Log.d("moi: ", "moi")
                                    /* TODO: nykynen sijainti vs latlng*/
                                    val workerRequest =
                                        OneTimeWorkRequestBuilder<NotificationRequestWorker>()
                                            .setInitialDelay(0, TimeUnit.SECONDS)
                                            .setInputData(data.build())
                                            .build()
                                     // Enqueue the above workrequest object to the WorkManager
                                    WorkManager.getInstance(context).enqueue(workerRequest)
                                    context.startActivity(
                                        Intent(
                                            context,
                                            MainActivity6::class.java
                                        ).putExtra("Message", title.value)
                                    )
                                }

                        }},
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Save reminder")
                }}}}}

fun ampm(): Long{
    val calendar: Calendar = Calendar.getInstance()
    if (Calendar.getInstance().get(Calendar.AM_PM)==1){
        calendar.set(Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH)+1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.HOUR)+12,
            Calendar.getInstance().get(Calendar.MINUTE))
        val nowTime = calendar.getTimeInMillis()
        return nowTime
    }
    if (Calendar.getInstance().get(Calendar.AM_PM)==0) {
        calendar.set(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.HOUR),
            Calendar.getInstance().get(Calendar.MINUTE)
        )
        val nowTime = calendar.getTimeInMillis()
        return nowTime
    }
    return 0
}
fun list(latLng: LatLng,string: String){
    val paikkay = latLng.longitude.toString()
    val paikkax = latLng.latitude.toString()
    val paikka = paikkax +" "+ paikkay
    lista.add(paikka)
    nimi.add(string)
}


private fun getCategoryId(categories: List<Category>, categoryName: String): Long {
    return categories.first { category -> category.name == categoryName }.id
}
