package com.example.mobilecomputinghomework.feature_notification.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mobilecomputinghomework.feature_notification.domain.ReminderScheduler
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@AndroidEntryPoint
class BootCompleteReceiver: BroadcastReceiver() {

    @Inject
    lateinit var reminderRepository: ReminderRepository

    @Inject
    lateinit var scheduler: ReminderScheduler

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED){
            setUpReminderNotifications(context!!)
        }
    }

    private fun setUpReminderNotifications(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            val reminders = reminderRepository.getRemindersWithNotification()
            reminders.forEach { reminder ->
                if (reminder.reminder_time!! > Clock.System.now().epochSeconds){
                    scheduler.schedule(
                        context = context,
                        reminderId = reminder.id!!,
                        reminderMessage = reminder.message,
                        reminderTime = reminder.reminder_time
                    )
                }
            }
        }
    }
}