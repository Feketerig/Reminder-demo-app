package com.example.mobilecomputinghomework.feature_reminder.domain

import com.example.mobilecomputinghomework.feature_reminder.data.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun insertReminder(reminderEntity: ReminderEntity): Long

    suspend fun getReminderById(id: Long): ReminderEntity

    fun getReminders(): Flow<List<ReminderEntity>>

    suspend fun deleteReminder(reminderEntity: ReminderEntity)

    suspend fun clearReminders()

    suspend fun getRemindersWithNotification(): List<ReminderEntity>

    suspend fun getRemindersWithLocation(): List<ReminderEntity>
}