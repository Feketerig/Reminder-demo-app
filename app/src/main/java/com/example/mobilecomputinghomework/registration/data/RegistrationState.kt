package com.example.mobilecomputinghomework.registration.data

data class RegistrationState(
    val email: String = "",
    val emailError: String? = null,
    val userName: String = "",
    val userNameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null
)