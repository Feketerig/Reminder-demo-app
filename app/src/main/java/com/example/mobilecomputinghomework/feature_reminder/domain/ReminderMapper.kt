package com.example.mobilecomputinghomework.feature_reminder.domain

import com.example.mobilecomputinghomework.feature_reminder.data.ReminderEntity
import kotlinx.datetime.Instant

fun ReminderEntity.toDomain(): Reminder = Reminder(
    id = id,
    message = message,
    location_x = location_x,
    location_y = location_y,
    reminder_time = Instant.fromEpochSeconds(reminder_time),
    creation_time = Instant.fromEpochSeconds(creation_time),
    creator_id = creator_id,
    reminder_seen = reminder_seen
)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id = id,
    message = message,
    location_x = location_x,
    location_y = location_y,
    reminder_time = reminder_time.toEpochMilliseconds(),
    creation_time = creation_time.toEpochMilliseconds(),
    creator_id = creator_id,
    reminder_seen = reminder_seen
)