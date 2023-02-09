package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import com.example.mobilecomputinghomework.feature_reminder.domain.toDomain
import com.example.mobilecomputinghomework.feature_reminder.domain.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderListViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
): ViewModel() {

    var reminders = flowOf(emptyList<Reminder>())

    private var recentlyDeletedReminder: Reminder? = null

    init {
        //Example data
        /*viewModelScope.launch {
            val list = (0..20).map { index ->
                Reminder(
                    id = index.toLong(),
                    message = index.toString(),
                    location_x = 0.0,
                    location_y = 0.0,
                    reminder_time = Instant.fromEpochSeconds(index.toLong()),
                    creation_time = Instant.fromEpochSeconds(index.toLong()),
                    creator_id = index.toLong(),
                    reminder_seen = false
                )
            }.forEach { reminder ->
                reminderRepository.insertReminder(reminder.toEntity())
            }
        }*/

        reminders = reminderRepository.getReminders().map { reminderEntityList ->
            reminderEntityList.map { reminderEntity ->
                reminderEntity.toDomain()
            }
        }
    }

    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch {
            reminderRepository.deleteReminder(reminder.toEntity())
            recentlyDeletedReminder = reminder
        }
    }

    fun restoreReminder(){
        viewModelScope.launch {
            reminderRepository.insertReminder(recentlyDeletedReminder?.toEntity() ?: return@launch)
            recentlyDeletedReminder = null
        }
    }

}