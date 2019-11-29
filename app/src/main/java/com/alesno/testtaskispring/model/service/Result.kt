package com.alesno.testtaskispring.model.service

import com.alesno.testtaskispring.model.response.ResponseJson

sealed class Result {
    data class Success(val responseJson: ResponseJson) : Result()
    data class Error(val e: Exception) : Result()
}