package com.example.mobilecomputinghomework.feature_notification.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mobilecomputinghomework.feature_notification.domain.ReminderScheduler
import com.example.mobilecomputinghomework.feature_reminder.domain.Reminder

class AndroidReminderScheduler(
    private val context: Context
): ReminderScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: Reminder) {
        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            putExtra("reminder_message", item.message)
            putExtra("reminder_id", item.id)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.reminder_time!!.toEpochMilliseconds(),
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}