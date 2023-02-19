package com.example.mobilecomputinghomework.feature_notification.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.mobilecomputinghomework.R
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderAlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var reminderRepository: ReminderRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("reminder_message") ?: return
        val id = intent.getLongExtra("reminder_id", -1)

        val newIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://reminders_detail/$id")
        )
        val pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context!!, "reminders")
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle("Reminder")
            .setContentText("$message $id")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }
}