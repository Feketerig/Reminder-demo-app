package com.example.mobilecomputinghomework.di

import com.example.mobilecomputinghomework.feature_authentication.data.AndroidEmailValidator
import com.example.mobilecomputinghomework.feature_authentication.domain.ValidateEmail
import com.example.mobilecomputinghomework.feature_authentication.domain.ValidatePassword
import com.example.mobilecomputinghomework.feature_authentication.domain.ValidateRepeatedPassword
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