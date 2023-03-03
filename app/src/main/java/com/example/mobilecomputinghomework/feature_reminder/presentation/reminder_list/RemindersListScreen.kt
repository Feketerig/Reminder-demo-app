package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_list

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilecomputinghomework.MainActivity
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder
import com.example.mobilecomputinghomework.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersListScreen(
    navHostController: NavController,
    viewModel: ReminderListViewModel = hiltViewModel()
) {
    var showMenu by remember { mutableStateOf(false) }

    var showDeleteAlert by remember { mutableStateOf<Reminder?>(null) }

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    var showDueDateReminders by remember { mutableStateOf(true) }

    var showMoreReminders by remember { mutableStateOf(false) }

    val airedReminders by viewModel.dueDateReminders.collectAsState(initial = emptyList())

    val notAiredReminders by viewModel.moreReminders.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navHostController.navigate(Screen.ReminderEdit.route) }) {
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
                                text = { Text(text = "Map") },
                                onClick = {
                                    showMenu = false
                                    navHostController.navigate(Screen.Maps.route)
                                })
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        if (showDeleteAlert != null) {
            DeleteAlert(
                onCancel = { showDeleteAlert = null },
                onDelete = {
                    viewModel.deleteReminder(showDeleteAlert ?: return@DeleteAlert)
                    showDeleteAlert = null
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Reminder deleted",
                            actionLabel = "Undo"
                        )
                        if(result == SnackbarResult.ActionPerformed) {
                            viewModel.restoreReminder()
                        }
                    }
                }
            )
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues))
        {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            showDueDateReminders = !showDueDateReminders
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Today",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    if (showDueDateReminders) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "show due date reminders",
                            modifier = Modifier.size(30.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "hide due date reminders",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
            //Due date reminders
            items(airedReminders) { reminder ->
                AnimatedVisibility(
                    visible = showDueDateReminders,
                    enter = slideInVertically(
                        animationSpec = tween(750)
                    ) + fadeIn(
                        animationSpec = tween(750)
                    ),
                    exit = slideOutVertically(
                        animationSpec = tween(750)
                    ) + fadeOut(
                        animationSpec = tween(750)
                    )
                ) {
                    ReminderComponent(
                        message = reminder.message,
                        deadline = reminder.reminder_time,
                        imagePath = reminder.imagePath,
                        onClick = {
                            navHostController.navigate(Screen.ReminderEdit.route + "?id=${reminder.id}")
                        },
                        onDelete = {
                            showDeleteAlert = reminder
                        }
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            showMoreReminders = !showMoreReminders
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Show more",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    if (showMoreReminders) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "show more"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "show less"
                        )
                    }
                }
            }
            //Rest of the reminders
            items(notAiredReminders) { reminder ->
                AnimatedVisibility(
                    visible = showMoreReminders,
                    enter = slideInVertically(
                        animationSpec = tween(750)
                    ) + fadeIn(
                        animationSpec = tween(750)
                    ),
                    exit = slideOutVertically(
                        animationSpec = tween(750)
                    ) + fadeOut(
                        animationSpec = tween(750)
                    )
                ) {
                    ReminderComponent(
                        message = reminder.message,
                        deadline = reminder.reminder_time,
                        imagePath = reminder.imagePath,
                        onClick = {
                            navHostController.navigate(Screen.ReminderEdit.route + "?id=${reminder.id}")
                        },
                        onDelete = {
                            showDeleteAlert = reminder
                        }
                    )
                }
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