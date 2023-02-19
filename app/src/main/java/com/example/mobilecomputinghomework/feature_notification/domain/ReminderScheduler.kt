package com.example.mobilecomputinghomework.feature_notification.domain

import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder

interface ReminderScheduler {
    fun schedule(item: Reminder)
}
