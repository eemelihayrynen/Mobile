package com.example.mobile

import android.annotation.SuppressLint
import com.example.mobile.model.Category
import com.example.mobile.model.RemindViewModel
import com.example.mobile.model.RemindViewState
import com.example.mobile.model.reminder
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mobile.*
import com.example.mobile.ui.theme.MobileTheme
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Remind3(
    hour:Int,
    min:Int,
    message: String,
    locationy: String,
    locationx: String,
    viewModel: RemindViewModel = viewModel() // uus viewmodel
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Log.d("message: ", message)
    val time = "$hour.$min"
    Log.d("time: ", time)
    coroutineScope.launch {
        Log.d("moro: ", "moro")

        viewModel.saveRemind( // uus viewmodel
            reminder(
                creation_time = 1,
                creator_id = 2,
                Message = message,
                location_x = locationx,
                location_y = locationy,
                reminder_time = time,
                reminder_seen = "reminder seen"

            )
        )
        context.startActivity(Intent(context, MainActivity6::class.java))
    }
}
private fun getCategoryId(categories: List<Category>, categoryName: String): Long {
    return categories.first { category -> category.name == categoryName }.id
}