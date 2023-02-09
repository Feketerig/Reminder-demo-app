package com.example.mobilecomputinghomework.feature_reminder.data

import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import kotlinx.coroutines.flow.Flow

class ReminderRepositoryImpl(
    private val reminderDao: ReminderDao
): ReminderRepository {
    override suspend fun insertReminder(reminderEntity: ReminderEntity) {
        reminderDao.insertReminder(reminderEntity)
    }

    override suspend fun getReminderById(id: Long): ReminderEntity {
        return reminderDao.getReminderById(id)
    }

    override fun getReminders(): Flow<List<ReminderEntity>> {
        return reminderDao.getReminders()
    }

    override suspend fun deleteReminder(reminderEntity: ReminderEntity) {
        reminderDao.deleteReminder(reminderEntity)
    }

    override suspend fun clearReminders() {
        reminderDao.clearReminders()
    }
}