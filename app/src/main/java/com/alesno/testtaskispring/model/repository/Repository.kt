package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.response.Response
import kotlinx.coroutines.Deferred

interface Repository {

    fun getResponseAsync(): Deferred<Response>

}