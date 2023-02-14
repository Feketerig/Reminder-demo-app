package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghomework.feature_reminder.data.toDomain
import com.example.mobilecomputinghomework.feature_reminder.data.toEntity
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import javax.inject.Inject

@HiltViewModel
class ReminderListViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
): ViewModel() {

    var dueDateReminders = flowOf(emptyList<Reminder>())

    var moreReminders = flowOf(emptyList<Reminder>())

    private var recentlyDeletedReminder: Reminder? = null

    init {
        dueDateReminders = reminderRepository.getReminders().map { reminderEntityList ->
            reminderEntityList.filter { reminderEntity ->
                reminderEntity.reminder_seen ||
                        reminderEntity.reminder_time?.let {
                            val date = Instant.fromEpochSeconds(it).toLocalDateTime(TimeZone.currentSystemDefault()).date
                            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            date == today
                        } ?: false
            }.map { reminderEntity ->
                reminderEntity.toDomain()
            }
        }
        moreReminders = reminderRepository.getReminders().map { reminderEntityList ->
            reminderEntityList.filterNot { reminderEntity ->
                reminderEntity.reminder_seen ||
                        reminderEntity.reminder_time?.let {
                            val date = Instant.fromEpochSeconds(it).toLocalDateTime(TimeZone.currentSystemDefault()).date
                            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            date == today
                        } ?: false
            }.map { reminderEntity ->
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