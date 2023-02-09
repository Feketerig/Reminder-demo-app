package com.example.mobilecomputinghomework.feature_reminder.reminder_list.presentation

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.mobilecomputinghomework.core.domain.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class ReminderListViewModel @Inject constructor(
    val reminderRepository: ReminderRepository
): ViewModel() {

    val list = (0..20).map { index ->
        com.example.mobilecomputinghomework.core.domain.Reminder(
            id = index.toLong(),
            message = index.toString(),
            location_x = 0.0,
            location_y = 0.0,
            reminder_time = Instant.fromEpochSeconds(index.toLong()),
            creation_time = Instant.fromEpochSeconds(index.toLong()),
            creator_id = index.toLong(),
            reminder_seen = false
        )
    }.toMutableStateList()

}