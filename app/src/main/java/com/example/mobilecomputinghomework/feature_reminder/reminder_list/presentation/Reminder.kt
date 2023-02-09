package com.example.mobilecomputinghomework.feature_reminder.reminder_list.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reminder(
    message: String,
    deadline: Instant,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.headlineLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${deadline.toLocalDateTime(TimeZone.currentSystemDefault()).date}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            IconButton(
                onClick = { onDelete() },
                modifier = Modifier.size(75.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    "",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ReminderPreview() {
    Reminder(
        message = "First",
        deadline = Instant.fromEpochSeconds(1),
        onClick = {},
        onDelete = {}
    )
}