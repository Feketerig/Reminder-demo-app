package com.example.mobilecomputinghomework.registration.domain

class ValidateRepeatedPassword {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords do not match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}