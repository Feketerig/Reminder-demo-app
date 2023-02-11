package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_list

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderComponent(
    message: String,
    deadline: Instant?,
    imagePath: Uri?,
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
                deadline?.let {time ->
                    Text(
                        text = "${time.toLocalDateTime(TimeZone.currentSystemDefault()).date}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            AsyncImage(
                model = imagePath,
                contentDescription = "Reminder picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
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
    ReminderComponent(
        message = "First",
        deadline = Instant.fromEpochSeconds(1),
        imagePath = null,
        onClick = {},
        onDelete = {}
    )
}