package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import com.example.mobilecomputinghomework.feature_reminder.domain.toDomain
import com.example.mobilecomputinghomework.feature_reminder.domain.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderEditViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var currentReminder by mutableStateOf(Reminder())

    init {
        savedStateHandle.get<Int>("id")?.let {id ->
            if (id != -1){
                viewModelScope.launch {
                    currentReminder = reminderRepository.getReminderById(id.toLong()).toDomain()
                }
            }
        }
    }

    fun onSave(){
        viewModelScope.launch {
            reminderRepository.insertReminder(currentReminder.toEntity())
        }
    }
}