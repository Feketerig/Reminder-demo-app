package com.example.mobilecomputinghomework.feature_authentication.domain

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)