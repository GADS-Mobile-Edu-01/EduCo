package com.android.educo.utils

import android.util.Patterns
import android.view.View
import kotlinx.android.synthetic.main.fragment_catalogue_docs.*
import java.util.regex.Pattern

/**
 * This string extension function return true if the reference string is a valid email address
 */
fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean {
//    // Regex to check valid password.
//    // Regex to check valid password.
//    val regex = ("^(?=.*[0-9])"
//            + "(?=.*[a-z])(?=.*[A-Z])"
//            + "(?=.*[@#$%^&+=])"
//            + "(?=\\S+$).{8,20}$")
//    val pattern = Pattern.compile(regex)
//    val matcher = pattern.matcher(this)
    return this.length >= 8
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}