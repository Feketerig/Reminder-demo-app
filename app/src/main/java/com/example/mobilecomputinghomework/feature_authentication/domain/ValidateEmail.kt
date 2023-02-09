package com.example.mobilecomputinghomework.feature_authentication.domain

class ValidateEmail(
    private val emailValidator: EmailPatternValidator
) {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "This field can not be empty"
            )
        }
        return if(emailValidator.isValidEmail(email)) {
            ValidationResult(
                successful = true
            )
        }else{
            ValidationResult(
                successful = false,
                errorMessage = "Not a valid email"
            )
        }
    }
}