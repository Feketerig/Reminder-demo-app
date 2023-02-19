package com.example.mobilecomputinghomework.di

import com.example.mobilecomputinghomework.feature_reminder.data.ReminderDao
import com.example.mobilecomputinghomework.feature_reminder.data.ReminderRepositoryImpl
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideReminderRepository(reminderDao: ReminderDao): ReminderRepository =
        ReminderRepositoryImpl(reminderDao)
}