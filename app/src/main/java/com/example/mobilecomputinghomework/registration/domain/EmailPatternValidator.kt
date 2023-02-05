package com.example.mobilecomputinghomework.registration.domain

interface EmailPatternValidator {
    fun isValidEmail(email:String): Boolean
}