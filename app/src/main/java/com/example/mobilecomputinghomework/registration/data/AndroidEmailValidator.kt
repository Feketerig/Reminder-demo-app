package com.example.mobilecomputinghomework.registration.data

import android.util.Patterns
import com.example.mobilecomputinghomework.registration.domain.EmailPatternValidator

class AndroidEmailValidator: EmailPatternValidator {
    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}