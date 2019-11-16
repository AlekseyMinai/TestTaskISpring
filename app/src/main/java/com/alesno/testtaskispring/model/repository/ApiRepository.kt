package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.response.Response
import com.alesno.testtaskispring.model.service.ApiService
import kotlinx.coroutines.Deferred

class ApiRepository(val service: ApiService): Repository{

    override fun getResponseAsync(): Deferred<Response>{
        return service.getResponseAsync()
    }

}