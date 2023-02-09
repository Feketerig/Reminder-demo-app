package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilecomputinghomework.MainActivity
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder
import com.example.mobilecomputinghomework.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersListScreen(
    navHostController: NavController,
    viewModel: ReminderListViewModel = hiltViewModel()
) {
    var showMenu by remember { mutableStateOf(false) }

    var showDeleteAlert by remember { mutableStateOf<Reminder?>(null) }

    val scope = rememberCoroutineScope()

    val reminders by viewModel.reminders.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new reminder",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Your reminders")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                actions = {
                    if (MainActivity.loggedIn) {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Menu"
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "Profile") },
                                onClick = {
                                    showMenu = false
                                    navHostController.navigate(Screen.Profile.route)
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Logout") },
                                onClick = {
                                    showMenu = false
                                    navHostController.navigate(Screen.Logout.route)
                                })
                        }
                    }
                }
            )
        },
    ) { paddingValues ->
        if (showDeleteAlert != null) {
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
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            items(reminders) { reminder ->
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