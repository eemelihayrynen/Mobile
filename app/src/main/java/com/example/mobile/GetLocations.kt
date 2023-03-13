package com.example.mobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.model.room.viewModelProviderFactoryOf

@Composable
fun getLocation(){
    val viewModel: categoryReminderViewModel = viewModel(
        key = "category_list_1",
        factory = viewModelProviderFactoryOf{categoryReminderViewModel(1)}
    )
    val viewState by viewModel.state.collectAsState()
    val list= viewState.reminders

}