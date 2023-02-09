package com.example.mobilecomputinghomework.feature_authentication.data

import android.util.Patterns
import com.example.mobilecomputinghomework.feature_authentication.domain.EmailPatternValidator

class AndroidEmailValidator: EmailPatternValidator {
    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}