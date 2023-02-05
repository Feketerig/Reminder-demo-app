package com.example.mobilecomputinghomework.registration.domain

class ValidateRepeatedPassword {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "A két jelszó nem egyezik meg"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}