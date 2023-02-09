package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder

@Composable
fun RemindersListScreen(
    viewModel: ReminderListViewModel = hiltViewModel()
) {
    var showDeleteAlert by remember { mutableStateOf<Reminder?>(null) }

    val scope = rememberCoroutineScope()

    val reminders by viewModel.reminders.collectAsState(initial = emptyList())

    if (showDeleteAlert != null){
        DeleteAlert(
            onCancel = { showDeleteAlert = null },
            onDelete = {
                viewModel.deleteReminder(showDeleteAlert ?: return@DeleteAlert)
                showDeleteAlert = null
                /*scope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = "Note deleted",
                        actionLabel = "Undo"
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.restoreReminder()
                    }
                }*/
            }
        )
    }
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(reminders){ reminder ->
            Reminder(
                message = reminder.message,
                deadline = reminder.reminder_time,
                onClick = {

                },
                onDelete = {
                    showDeleteAlert = reminder
                }
            )
        }
    }
}

@Composable
fun CreateReminder() {
    
}

@Composable
fun DeleteAlert(
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onCancel()
        },
        title = {
            Text(text = "Attention!")
        },
        text = {
            Text("Are you sure you want to delete this reminder?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                }) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onCancel()
                }) {
                Text("Cancel")
            }
        }
    )
}