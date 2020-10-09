package com.android.educo.views.main.utils

import android.util.Patterns

/**
 * This string extension function return true if the reference string is a valid email address
 */
fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()