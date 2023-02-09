package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderEditScreen(
    navHostController: NavHostController,
    viewModel: ReminderEditViewModel = hiltViewModel()
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::onSave ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save reminder",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = viewModel.currentReminder.message,
                onValueChange = { viewModel.currentReminder = viewModel.currentReminder.copy(message = it)}
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}