package com.sarthak.dictionary.util

sealed class Result<T>(
    val data: T? = null,
    val message: String = ""
) {
    class Success<T>(data: T?) : Result<T>(data)

    class Error<T>(message: String) : Result<T>(null, message)

    class Loading<T>() : Result<T>(null)
}