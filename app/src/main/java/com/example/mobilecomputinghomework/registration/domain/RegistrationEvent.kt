package com.example.mobilecomputinghomework.registration.domain

sealed class RegistrationEvent {
    data class EmailChanged(val email: String) : RegistrationEvent()
    data class UserNameChanged(val userName: String): RegistrationEvent()
    data class PasswordChanged(val password: String) : RegistrationEvent()
    data class RepeatedPasswordChanged(
        val repeatedPassword: String
    ) : RegistrationEvent()

    object Login: RegistrationEvent()
}