@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.mobile
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile.model.reminder
import com.example.mobile.model.ReminderViewModelAbstract
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    reminderViewModel: ReminderViewModelAbstract,
    onClickNote: (reminder) -> Unit,
    onClickAddNote: () -> Unit,
) {
    val reminderListState = reminderViewModel.reminderListFlow.collectAsState(initial = listOf())
    val txtState = rememberSaveable { mutableStateOf("") }
    val noteIdState: MutableState<Long?> = rememberSaveable { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home")
                },

                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = null
                    )
                }
            )

        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .animateContentSize(),
        ) {
            items(
                items = reminderListState.value,
                key = { it.creator_id ?: "" }
            ) { Reminder ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart ||
                            it == DismissValue.DismissedToEnd
                        ) {
                            reminderViewModel.deleteReminder(Reminder)

                            return@rememberDismissState true
                        }
                        return@rememberDismissState false
                    }
                )
            }
            item(key = "add_button") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                {
                    Button(modifier = Modifier
                        .align(Alignment.Center),
                        onClick = {
                            reminderViewModel.resetSelectedReminder()
                            onClickAddNote
                        }) {
                        Text(text = stringResource(id = R.string.add_note))

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        reminderViewModel = object : ReminderViewModelAbstract {
            override val selectedState: State<reminder?>
                get() = mutableStateOf(null)
            override val reminderListFlow: Flow<List<reminder>>
                get() = flowOf(
                    listOf(
                        reminder(Message = "Wakeup 1", location_x= "Wakeup", location_y= "Wakeup", reminder_time = "4:10", creation_time= "5", creator_id= 5, reminder_seen= "Wakeup"),
                        reminder(Message = "Wakeup 2", location_x= "Wakeup", location_y= "Wakeup", reminder_time = "4:12", creation_time= "5", creator_id= 6, reminder_seen= "Wakeup"),
                        reminder(Message = "Wakeup 3", location_x= "Wakeup", location_y= "Wakeup", reminder_time = "4:13", creation_time= "5", creator_id= 7, reminder_seen= "Wakeup")

                    )
                )

            override fun addOrUpdateReminder(Reminder: reminder) {}
            override fun deleteReminder(Reminder: reminder) {}
            override fun selectReminder(Reminder: reminder) {}
            override fun resetSelectedReminder() {}
        },
        onClickNote = {},
        onClickAddNote = {},
    )
}
