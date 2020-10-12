package com.android.educo.utils

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T, val message: String = "") : Resource<T>()
    data class Failure<out T>(val message: String) : Resource<T>()
}