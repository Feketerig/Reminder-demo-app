package com.example.mobilecomputinghomework.reminderlist.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant

@Composable
fun RemindersListScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(20){index ->
            Reminder(title = index.toString(), deadline = Instant.fromEpochSeconds(index.toLong())) {

            }
        }
    }
}