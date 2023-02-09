package com.example.mobilecomputinghomework.feature_authentication.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isName: Boolean = false,
)
