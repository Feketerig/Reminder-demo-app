package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_edit

import android.Manifest
import android.content.Context.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.text.format.DateFormat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderEditScreen(
    navHostController: NavHostController,
    viewModel: ReminderEditViewModel = hiltViewModel()
) {
    val currentReminder by viewModel.currentReminder.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var date by remember {
        mutableStateOf(0L)
    }
    var messageIsEmptyError by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri!!, flag)
            viewModel.onUriChange(uri)
        }
    )

    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
        }
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    if (currentReminder.message.isNotEmpty()) {
                        viewModel.onSave()
                        /*if (viewModel.currentReminder.id == null) {
                            val startDate =
                                viewModel.currentReminder.reminder_time?.toEpochMilliseconds()
                            val endDate = viewModel.currentReminder.reminder_time?.plus(1.hours)
                                ?.toEpochMilliseconds()
                            val mIntent = Intent(Intent.ACTION_EDIT)
                            mIntent.type = "vnd.android.cursor.item/event"
                            mIntent.putExtra("beginTime", startDate)
                            mIntent.putExtra("time", true)
                            mIntent.putExtra("endTime", endDate)
                            mIntent.putExtra("title", viewModel.currentReminder.message)
                            context.startActivity(mIntent)
                        }*/
                        navHostController.navigateUp()
                    }else{
                        messageIsEmptyError = true
                    }
            } ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save reminder",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (currentReminder.id != null){
                        Text(text = "Edit reminder")
                    }else{
                        Text(text = "Create reminder")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Reminder message: ")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = currentReminder.message,
                onValueChange = { message ->
                    messageIsEmptyError = false
                    viewModel.onMessageTextChange(message)
                },
                isError = messageIsEmptyError,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Message")
                }
            )
            if (messageIsEmptyError){
                Text(
                    text = "Message can not be empty",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Selected alert time: ")
                currentReminder.reminder_time?.let {time ->
                    val localDateTime = time.toLocalDateTime(TimeZone.currentSystemDefault())
                    val formattedLocalDateTime = localDateTime.toString().replace("T", " ")
                    Text(text = formattedLocalDateTime)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { showDatePicker = true }) {
                    Text(text = "Pick date and time")
                }
                if (currentReminder.reminder_time != null) {
                    IconButton(onClick = viewModel::onReminderTimeDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete time",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onClick = { selectedDateInMillis ->
                        showDatePicker = false
                        date = selectedDateInMillis
                        showTimePicker = true
                    },
                    onDismiss = {
                        showDatePicker = false
                    }
                )
            }
            if (showTimePicker) {
                TimePickerDialog(
                    onClick = { hour, minute ->
                        viewModel.setReminderTime(date, hour, minute)
                        showTimePicker = false
                    },
                    onDismiss = {
                        showTimePicker = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                if (currentReminder.imagePath == null) {
                    Text(text = "Pick a photo")
                }else{
                    Text(text = "Change photo")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (currentReminder.imagePath != null){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = currentReminder.imagePath,
                        contentDescription = "Reminder picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp),
                    )
                    IconButton(onClick = viewModel::onUriDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete image",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onClick: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled by remember{ derivedStateOf { datePickerState.selectedDateMillis != null }}
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onClick(datePickerState.selectedDateMillis!!) },
                enabled = confirmEnabled
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onClick: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val is24HourFormat by rememberUpdatedState(DateFormat.is24HourFormat(context))
    val state = rememberTimePickerState(is24Hour = is24HourFormat)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Choose time!")
        },
        text = {
            TimePicker(state = state)
        },
        confirmButton = {
            Button(
                onClick = {
                    onClick(state.hour, state.minute)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}