package com.example.mobilecomputinghomework.di

import com.example.mobilecomputinghomework.core.data.ReminderDao
import com.example.mobilecomputinghomework.core.data.ReminderRepositoryImpl
import com.example.mobilecomputinghomework.core.domain.ReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideReminderRepository(reminderDao: ReminderDao): ReminderRepository =
        ReminderRepositoryImpl(reminderDao)
}