package com.example.mobilecomputinghomework.feature_reminder.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReminder(reminderEntity: ReminderEntity)

    @Query("select * from reminders where id = :id")
    suspend fun getReminderById(id: Long): ReminderEntity

    @Query("select * from reminders")
    fun getReminders(): Flow<List<ReminderEntity>>

    @Delete
    suspend fun deleteReminder(reminderEntity: ReminderEntity)

    @Query("delete from reminders")
    suspend fun clearReminders()

}