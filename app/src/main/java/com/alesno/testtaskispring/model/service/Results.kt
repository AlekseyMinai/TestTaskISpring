package com.alesno.testtaskispring.model.service

sealed class Results<T> {
    data class Success<T>(val successResult: T) : Results<T>()
    data class Error<T>(val message: String) : Results<T>()
}