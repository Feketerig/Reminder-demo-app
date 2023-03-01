package com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_edit

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghomework.feature_notification.domain.ReminderScheduler
import com.example.mobilecomputinghomework.feature_reminder.data.toDomain
import com.example.mobilecomputinghomework.feature_reminder.data.toEntity
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
class ReminderEditViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val scheduler: ReminderScheduler,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _currentReminder = MutableStateFlow(Reminder())
    val currentReminder = _currentReminder.asStateFlow()

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    _currentReminder.value = reminderRepository.getReminderById(id.toLong()).toDomain()
                }
            }
        }
    }

    fun onSave(context: Context) {
        viewModelScope.launch {
            _currentReminder.update {
                it.copy(creation_time = Clock.System.now())
            }
            val id = reminderRepository.insertReminder(currentReminder.value.toEntity())
            _currentReminder.update {
                it.copy(id = id)
            }
            if (currentReminder.value.addNotification){
                scheduler.schedule(
                    context = context,
                    reminderId = currentReminder.value.id!!,
                    reminderMessage = currentReminder.value.message,
                    reminderTime = currentReminder.value.reminder_time!!.epochSeconds
                )
            }
            if (currentReminder.value.addCalendarEvent) {
                val startDate = currentReminder.value.reminder_time?.toEpochMilliseconds()
                val endDate = currentReminder.value.reminder_time?.plus(1.hours)?.toEpochMilliseconds()
                val mIntent = Intent(Intent.ACTION_EDIT)
                mIntent.type = "vnd.android.cursor.item/event"
                mIntent.putExtra("beginTime", startDate)
                mIntent.putExtra("time", true)
                mIntent.putExtra("endTime", endDate)
                mIntent.putExtra("title", currentReminder.value.message)
                context.startActivity(mIntent)
            }
        }
    }

    fun setReminderTime(datePickerInMillis: Long, hour: Int, minute: Int) {
        val date = Instant.fromEpochMilliseconds(datePickerInMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
        val dateTime = LocalDateTime(date.year, date.monthNumber, date.dayOfMonth, hour, minute)
        _currentReminder.update {
            it.copy(reminder_time = dateTime.toInstant(TimeZone.currentSystemDefault()))
        }
    }

    fun onMessageTextChange(message: String){
        _currentReminder.update {
            it.copy(message = message)
        }
    }

    fun onReminderTimeDelete(){
        _currentReminder.update {
            it.copy(reminder_time = null)
        }
    }

    fun onUriChange(uri: Uri){
        _currentReminder.update {
            it.copy(imagePath = uri)
        }
    }

    fun onUriDelete(){
        _currentReminder.update {
            it.copy(imagePath = null)
        }
    }

    fun onAddCalendarEventChange(checked: Boolean){
        _currentReminder.update {
            it.copy(addCalendarEvent = checked)
        }
    }

    fun onSetNotificationChange(checked: Boolean){
        _currentReminder.update {
            it.copy(addNotification = checked)
        }
    }

    fun setGpsPosition(markerPosition: LatLng, radius: Int) {
        _currentReminder.update {
            it.copy(
                lat = markerPosition.latitude,
                lon = markerPosition.longitude,
                radius = radius
            )
        }
    }
}