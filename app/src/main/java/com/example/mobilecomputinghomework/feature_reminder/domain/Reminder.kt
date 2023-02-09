package com.example.mobilecomputinghomework.feature_reminder.domain

import kotlinx.datetime.Instant

data class Reminder(
    val id: Long,
    val message: String,
    val location_x: Double,
    val location_y: Double,
    val reminder_time: Instant,
    val creation_time: Instant,
    val creator_id: Long,
    val reminder_seen: Boolean
)
