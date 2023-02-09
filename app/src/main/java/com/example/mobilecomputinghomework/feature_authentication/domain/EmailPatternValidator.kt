package com.example.mobilecomputinghomework.feature_authentication.domain

interface EmailPatternValidator {
    fun isValidEmail(email:String): Boolean
}