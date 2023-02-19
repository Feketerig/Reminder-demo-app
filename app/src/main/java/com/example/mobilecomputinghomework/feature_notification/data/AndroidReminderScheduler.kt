package com.example.mobilecomputinghomework.feature_notification.data

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mobilecomputinghomework.feature_notification.domain.ReminderScheduler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AndroidReminderScheduler: ReminderScheduler, BroadcastReceiver() {
    override fun schedule(context: Context, reminderId: Long, reminderMessage: String, reminderTime: Long) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            putExtra("reminder_message", reminderMessage)
            putExtra("reminder_id", reminderId)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            reminderTime * 1000L,
            PendingIntent.getBroadcast(
                context,
                reminderId.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("reminder_message") ?: return
        val id = intent.getLongExtra("reminder_id", -1)
        val time = intent.getLongExtra("reminder_time", 0)
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(id.toInt())
        schedule(context, id, message, time)
    }
}