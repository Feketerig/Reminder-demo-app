package com.example.mobilecomputinghomework.feature_reminder.domain

import android.net.Uri
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Reminder(
    val id: Long? = null,
    val message: String = "",
    val lat: Double? = null,
    val lon: Double? = null,
    val radius: Int? = null,
    val reminder_time: Instant? = null,
    val creation_time: Instant = Clock.System.now(),
    val creator_id: Long = 1L,
    val reminder_seen: Boolean = false,
    val addCalendarEvent: Boolean = false,
    val addNotification: Boolean = true,
    val imagePath: Uri? = null
)
