package com.example.mobilecomputinghomework.feature_reminder.data

import androidx.core.net.toUri
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder
import kotlinx.datetime.Instant

fun ReminderEntity.toDomain(): Reminder = Reminder(
    id = id,
    message = message,
    location_x = location_x,
    location_y = location_y,
    reminder_time = reminder_time?.let { Instant.fromEpochSeconds(it) },
    creation_time = Instant.fromEpochSeconds(creation_time),
    creator_id = creator_id,
    reminder_seen = reminder_seen,
    addCalendarEvent = addCalendarEvent,
    addNotification = addNotification,
    imagePath = imagePath?.toUri()
)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id = id,
    message = message,
    location_x = location_x,
    location_y = location_y,
    reminder_time = reminder_time?.epochSeconds,
    creation_time = creation_time.epochSeconds,
    creator_id = creator_id,
    reminder_seen = reminder_seen,
    addCalendarEvent = addCalendarEvent,
    addNotification = addNotification,
    imagePath = imagePath?.toString()
)