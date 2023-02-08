package com.example.mobilecomputinghomework.core.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ReminderEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class ReminderDatabase: RoomDatabase(){
    abstract val reminderDao: ReminderDao
}