package com.example.mobilecomputinghomework.feature_authentication.domain

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must be at least 8 digits long"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must contain at least one number and one letter"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}