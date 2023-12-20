package com.bangkit.martq.utils.customView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EmailEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!isValidEmail(s.toString())) {
            setError("Email tidak valid", null)
        } else {
            error = null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailParts = email.split("@")
        if (emailParts.size != 2) {
            return false
        }

        val localPart = emailParts[0]
        val domainPart = emailParts[1]

        if (localPart.isEmpty() || domainPart.isEmpty()) {
            return false
        }

        val domainParts = domainPart.split(".")
        if (domainParts.size < 2) {
            return false
        }

        for (part in domainParts) {
            if (part.isEmpty()) {
                return false
            }
        }

        return true
    }
}