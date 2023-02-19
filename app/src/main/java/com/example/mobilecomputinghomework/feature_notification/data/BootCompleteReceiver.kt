package com.example.mobilecomputinghomework.feature_notification.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootCompleteReceiver: BroadcastReceiver() {

    @Inject
    lateinit var reminderRepository: ReminderRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED){
            //TODO restart alarm managers
        }
    }
}