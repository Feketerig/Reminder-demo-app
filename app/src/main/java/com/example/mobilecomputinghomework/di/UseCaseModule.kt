package com.example.mobilecomputinghomework.di

import com.example.mobilecomputinghomework.registration.data.AndroidEmailValidator
import com.example.mobilecomputinghomework.registration.domain.ValidateEmail
import com.example.mobilecomputinghomework.registration.domain.ValidatePassword
import com.example.mobilecomputinghomework.registration.domain.ValidateRepeatedPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideValidateEmail(): ValidateEmail {
        return ValidateEmail(AndroidEmailValidator())
    }

    @Provides
    @Singleton
    fun provideValidatePassword(): ValidatePassword {
        return ValidatePassword()
    }

    @Provides
    @Singleton
    fun provideValidateRepeatedPassword(): ValidateRepeatedPassword {
        return ValidateRepeatedPassword()
    }
}