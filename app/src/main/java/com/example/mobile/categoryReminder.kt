package com.example.mobile

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.navigation.compose.composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.model.Category
import com.example.mobile.model.RemindViewModel
import com.example.mobile.model.ReminderToCategory
import com.example.mobile.model.reminder
import com.example.mobile.model.room.viewModelProviderFactoryOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.coroutineScope
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
@Composable
fun CategoryReminder(
    categoryId: Long,
    modifier: Modifier = Modifier
) {
    val viewModel: categoryReminderViewModel = viewModel(
        key = "category_list_$categoryId",
        factory =viewModelProviderFactoryOf{categoryReminderViewModel(categoryId)}
    )
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        PaymentList(
            list = viewState.reminders
        )
    }
}

@Composable
private fun PaymentList(
    list: List<ReminderToCategory>
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            PaymentListItem(
                Reminder = item.Reminder,
                category = item.category,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth(),
            )
        }
    }
}

@Composable
private fun PaymentListItem(
    Reminder : reminder,
    category: Category,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RemindViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, paymentTitle, paymentCategory, icon, date) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // title
        Text(
            text = Reminder.reminder_time,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(paymentTitle) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 50.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        // category
        Text(
            text = Reminder.Message,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(paymentCategory) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 50.dp,
                    endMargin = 8.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(paymentTitle.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // icon
        IconButton(
            onClick = {
              coroutineScope.launch {
                  viewModel.deleteRemind(Reminder)
              }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end, 20.dp)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "check"
            )
        }
        val context = LocalContext.current
        IconButton(

            onClick = {coroutineScope.launch {
                viewModel.deleteRemind(Reminder)}
                context.startActivity(Intent(context,MainActivity4::class.java))
            },
            modifier = Modifier
                .size(50.dp)
                .padding(0.dp)

        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "edit"
            )
        }



    }
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(this)
}

private fun Long.toDateString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(this))

}