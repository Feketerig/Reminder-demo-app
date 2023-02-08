package com.example.mobilecomputinghomework.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val message: String,
    val location_x: Double,
    val location_y: Double,
    val reminder_time: Long,
    val creation_time: Long,
    val creator_id: Long,
    val reminder_seen: Boolean
)
