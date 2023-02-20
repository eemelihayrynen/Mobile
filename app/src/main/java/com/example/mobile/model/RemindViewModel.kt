package com.example.mobile.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.Graph
import com.example.mobile.repository.RemindRepository
import com.example.mobile.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RemindViewModel (
    private val remindRepository:RemindRepository = Graph.remindRepository,
    private val reminderRepository: ReminderRepository= Graph.reminderRepository
): ViewModel()
{
    private val _state = MutableStateFlow(RemindViewState())

    val state: StateFlow<RemindViewState>
    get() = _state

    suspend fun saveRemind(Reminder: reminder): Long {
        return remindRepository.addReminder(Reminder)
    }

    suspend fun deleteRemind(reminder: reminder): Int {
        return remindRepository.deleteReminder(reminder)
    }

    init {
        viewModelScope.launch {
            reminderRepository.categories().collect{ categories ->
                _state.value = RemindViewState(categories)
            }
        }
    }
}

data class RemindViewState(
    val categories: List<Category> = emptyList()
)