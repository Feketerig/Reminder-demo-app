package com.example.mobilecomputinghomework.feature_authentication.presentation.login

sealed class LoginEvent{
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()

    object Login: LoginEvent()
    object CancelLogout: LoginEvent()
    object Logout: LoginEvent()
}
