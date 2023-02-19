package com.example.mobilecomputinghomework.feature_notification.domain

import android.content.Context

interface ReminderScheduler {
    fun schedule(context: Context, reminderId: Long, reminderMessage: String, reminderTime: Long)
}
