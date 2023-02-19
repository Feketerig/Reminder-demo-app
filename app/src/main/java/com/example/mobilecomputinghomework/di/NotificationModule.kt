package com.example.mobilecomputinghomework.di

import android.content.Context
import com.example.mobilecomputinghomework.feature_notification.data.AndroidReminderScheduler
import com.example.mobilecomputinghomework.feature_notification.domain.ReminderScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideAndroidReminderScheduler(@ApplicationContext context: Context): ReminderScheduler =
        AndroidReminderScheduler(context)
}