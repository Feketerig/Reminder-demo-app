package com.example.mobilecomputinghomework.core.domain

import com.example.mobilecomputinghomework.core.data.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun insertReminder(reminderEntity: ReminderEntity)

    suspend fun getReminderById(id: Long): ReminderEntity

    fun getReminders(): Flow<List<ReminderEntity>>

    suspend fun deleteReminder(reminderEntity: ReminderEntity)

    suspend fun clearReminders()
}