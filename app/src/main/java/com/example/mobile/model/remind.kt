package com.example.mobile.model

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mobile.NotificationRequestWorker
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


@Composable
fun Remind(
    onBackPress: () -> Unit,
    viewModel: RemindViewModel = viewModel() // uus viewmodel
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val title = rememberSaveable { mutableStateOf("") }
    val category = rememberSaveable { mutableStateOf("") }
    val amount = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
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
                OutlinedTextField(
                    value = amount.value,
                    onValueChange = { amount.value = it },
                    label = { Text(text = "time")},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    enabled = true,
                    onClick = {

                        val minutes = minutes(amount.value)
                        Log.d("minutes: ", minutes.toString())
                        val hours = hours(amount.value)
                        Log.d("hours: ", hours.toString())
                        val seconds = 2
                        Log.d("sekunnit: ", seconds.toString())
                        val workerRequest = OneTimeWorkRequestBuilder<NotificationRequestWorker>()
                            .setInitialDelay(seconds.toLong(), TimeUnit.SECONDS)
                            .build()
                        // Enqueue the above workrequest object to the WorkManager
                        WorkManager.getInstance(context).enqueue(workerRequest)

                        coroutineScope.launch {
                            viewModel.saveRemind( // uus viewmodel
                                reminder(
                                    creation_time = 0,
                                    creator_id = getCategoryId(viewState.categories,"Reminders"),
                                    Message = title.value,
                                    location_x = "location x",
                                    location_y = "location y",
                                    reminder_time = amount.value,
                                    reminder_seen = "reminder seen"
                                )
                            )
                        }
                        onBackPress()
                        onBackPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Save reminder")
                }
            }
        }
    }
}
fun minutes(minat:String):Long {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val currentTime = LocalDateTime.now().format(formatter)
    val split = currentTime.split(":")
    val minutesNow = split[split.size-2]+""+split[split.size-1]
    val splitted = minat.split(".")
    val minutes = splitted[splitted.size-2]+""+splitted[splitted.size-1]
    if (minutes.toLong()<minutesNow.toLong()){
        return 1
    }
    if (minutes.toLong()>minutesNow.toLong()){
        return 1
    }
    return 0
}
fun hours(tunnit: String):Long  {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val currentTime = LocalDateTime.now().format(formatter)
    val split = currentTime.split(":").toString()
    val hoursNow = split[1] + "" + split[2]
    val splitted = tunnit.split(".").toString()
    val hours = splitted[5] + "" + splitted[6]
    Log.wtf("hours: ", hours)
    Log.wtf("hoursNow: ", hoursNow)
    if (hours.toLong() < hoursNow.toLong()) {
        return 0

    }
    if (hours.toLong() > hoursNow.toLong()) {
        return 0
    }
    return 0
}
private fun getCategoryId(categories: List<Category>, categoryName: String): Long {
    return categories.first { category -> category.name == categoryName }.id
}
@Composable
private fun CategoryListDropdown(
    viewState: RemindViewState,
    category: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp // requires androidx.compose.material:material-icons-extended dependency
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column {
        OutlinedTextField(
            value = category.value,
            onValueChange = { category.value = it},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Category") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            viewState.categories.forEach { dropDownOption ->
                DropdownMenuItem(
                    onClick = {
                        category.value = dropDownOption.name
                        expanded = false
                    }
                ) {
                    Text(text = dropDownOption.name)
                }

            }
        }
    }
}