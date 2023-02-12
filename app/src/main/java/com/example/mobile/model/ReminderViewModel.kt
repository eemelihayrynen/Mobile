package com.example.mobile.model

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface ReminderViewModelAbstract {
    val selectedState: State<reminder?>
    val reminderListFlow: Flow<List<reminder>>
    fun addOrUpdateReminder(Reminder: reminder)
    fun deleteReminder(Reminder: reminder)
    fun selectReminder(Reminder: reminder)
    fun resetSelectedReminder()
}
@HiltViewModel
class ReminderViewModel
@Inject constructor(
    private val reminderRepository: ReminderRepository,
): ViewModel(),ReminderViewModelAbstract {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _selectedState: MutableState<reminder?> = mutableStateOf(null)
    override val selectedState: State<reminder?>
        get() = _selectedState

    override val reminderListFlow: Flow<List<reminder>> = reminderRepository.readAllData()

    override fun addOrUpdateReminder(Reminder: reminder) {
        ioScope.launch {
            if (Reminder.creator_id == null) {
                reminderRepository.addReminder(Reminder)
            } else {
                reminderRepository.updateReminder(Reminder)
            }
        }
    }

    override fun deleteReminder(Reminder: reminder) {
        _selectedState.value = Reminder
    }

    override fun selectReminder(Reminder: reminder) {
        _selectedState.value = Reminder
    }

    override fun resetSelectedReminder() {
        _selectedState.value = null
    }
}
