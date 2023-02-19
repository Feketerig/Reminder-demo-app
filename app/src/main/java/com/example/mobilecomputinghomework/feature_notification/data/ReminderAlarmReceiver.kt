package com.example.mobilecomputinghomework.feature_notification.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.mobilecomputinghomework.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.Clock

@AndroidEntryPoint
class ReminderAlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("reminder_message") ?: return
        val id = intent.getLongExtra("reminder_id", -1)

        val detailIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://reminders_detail/$id")
        )
        val pendingIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_IMMUTABLE)

        val snoozeIntent = Intent(context?.applicationContext, AndroidReminderScheduler::class.java).apply {
            putExtra("reminder_message", message)
            putExtra("reminder_id", id)
            putExtra("reminder_time", Clock.System.now().epochSeconds + 5*60)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context?.applicationContext,
            id.toInt(),
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context!!, "reminders")
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle("Reminder")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.baseline_alarm_24, "Snooze for 5 minutes", snoozePendingIntent)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id.toInt(), notification)
    }
}