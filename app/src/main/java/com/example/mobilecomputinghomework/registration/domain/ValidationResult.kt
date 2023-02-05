package com.example.mobilecomputinghomework.registration.domain

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)