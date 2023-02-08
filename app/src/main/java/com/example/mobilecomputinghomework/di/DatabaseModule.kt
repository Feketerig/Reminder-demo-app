package com.example.mobilecomputinghomework.di

import android.content.Context
import androidx.room.Room
import com.example.mobilecomputinghomework.core.data.ReminderDao
import com.example.mobilecomputinghomework.core.data.ReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesReminderDatabase(@ApplicationContext context: Context): ReminderDatabase =
        Room.databaseBuilder(
            context,
            ReminderDatabase::class.java,
            "reminder-database"
        ).build()

    @Provides
    fun providesReminderDao(database: ReminderDatabase): ReminderDao =
        database.reminderDao
}