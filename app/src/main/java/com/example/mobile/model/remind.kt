package com.example.mobile.model

import android.content.Intent

import android.util.Log
import android.widget.CheckBox
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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
import androidx.navigation.compose.composable
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mobile.*
import com.example.mobile.ui.theme.MobileTheme
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule


@Composable
fun Remind(
    onBackPress: () -> Unit,
    viewModel: RemindViewModel = viewModel() // uus viewmodel
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val title = rememberSaveable { mutableStateOf("") }
    val amount = rememberSaveable { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val data = Data.Builder()
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
                        if (amount.value != "" && isChecked.value){
                            data.putString("message",title.value)
                            val split = amount.value.split(".")
                            val hour = split[0]
                            val min = split[1]
                            val calendar: Calendar = Calendar.getInstance()
                            calendar.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)+1,Calendar.getInstance().get(Calendar.DAY_OF_MONTH),hour.toInt(),min.toInt())
                            val goalTime = calendar.getTimeInMillis()
                            val nowTime = ampm()
                            val milseconds = goalTime-nowTime
                            val seconds = milseconds/1000
                            Log.d("seconds: ", seconds.toString())
                            val workerRequest = OneTimeWorkRequestBuilder<NotificationRequestWorker>()
                                .setInitialDelay(seconds, TimeUnit.SECONDS)
                                .setInputData(data.build())
                                .build()
                            // Enqueue the above workrequest object to the WorkManager
                            WorkManager.getInstance(context).enqueue(workerRequest)
                            context.startActivity(Intent(context, MainActivity6::class.java).putExtra("Message",title.value))
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
                            Timer().schedule(milseconds){
                                context.startActivity(Intent(context, MainActivity5::class.java).putExtra("hour",hour).putExtra("message",title.value).putExtra("min",min))
                            }

                            context.startActivity(
                                Intent(
                                    context,
                                    MainActivity6::class.java
                                ).putExtra("Message", title.value)
                            )
                        }
                        if (amount.value == ""){
                            coroutineScope.launch {
                                viewModel.saveRemind( // uus viewmodel
                                    reminder(
                                        creation_time = 0,
                                        creator_id = getCategoryId(viewState.categories,"Reminders"),
                                        Message = title.value,
                                        location_x = "location x",
                                        location_y = "location y",
                                        reminder_time = "No time",
                                        reminder_seen = "reminder seen"
                                    )
                                )

                                context.startActivity(Intent(context, MainActivity6::class.java).putExtra("Message",title.value))
                            }
                            }
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

@Composable
fun labelledCheckbox() {
    Row(modifier = Modifier.padding(0.dp)) {
        val isChecked = remember { mutableStateOf(false) }

        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            enabled = true,
            colors = CheckboxDefaults.colors(androidx.compose.ui.graphics.Color.Green)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Notification",Modifier.padding(10.dp))
    }
}
@Composable
fun labelledCheckbox2() {
    Row(modifier = Modifier.padding(0.dp)) {
        val isChecked = remember { mutableStateOf(false) }

        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            enabled = true,
            colors = CheckboxDefaults.colors(androidx.compose.ui.graphics.Color.Green)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Make daily",Modifier.padding(10.dp))

    }

}
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