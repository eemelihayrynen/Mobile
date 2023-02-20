package com.example.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.model.ReminderToCategory
import com.example.mobile.repository.RemindRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class categoryReminderViewModel(
    private val categoryId: Long,
    private val remindRepository: RemindRepository = Graph.remindRepository
) : ViewModel() {
    private val _state = MutableStateFlow(categoryReminderViewState())

    val state: StateFlow<categoryReminderViewState>
        get() = _state

    init {
        viewModelScope.launch {
            remindRepository.remindersInCategory(categoryId).collect{ list ->
                _state.value = categoryReminderViewState(
                    reminders = list
                )
            }
        }
    }
}

data class categoryReminderViewState(
    val reminders: List<ReminderToCategory> = emptyList()
)