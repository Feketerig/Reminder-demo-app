package com.example.mobilecomputinghomework.feature_location.data

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mobilecomputinghomework.feature_notification.data.ReminderAlarmReceiver
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GeofenceBroadcastReceiver: BroadcastReceiver() {

    @Inject
    lateinit var reminderRepository: ReminderRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "ACTION_GEOFENCE_EVENT") {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)!!

            if (geofencingEvent.hasError()) {
                return
            }

            if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

                val fenceId = when {
                    geofencingEvent.triggeringGeofences?.isNotEmpty() == true ->
                        geofencingEvent.triggeringGeofences?.get(0)?.requestId
                    else -> {
                        return
                    }
                }
                CoroutineScope(Dispatchers.IO).launch {
                    val reminder = reminderRepository.getReminderById(fenceId!!.toLong())
                    val broadcastIntent = Intent(context, ReminderAlarmReceiver::class.java).apply {
                        putExtra("reminder_message", reminder.message)
                        putExtra("reminder_id", reminder.id)
                    }
                    val broadcastPendingIntent = PendingIntent.getBroadcast(
                        context.applicationContext,
                        reminder.id?.toInt() ?: 0,
                        broadcastIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    broadcastPendingIntent.send()
                }

            }
        }
    }
}