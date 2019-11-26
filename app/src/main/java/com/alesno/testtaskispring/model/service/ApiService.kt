package com.alesno.testtaskispring.model.service

import com.alesno.testtaskispring.common.REQUEST_PARAM
import com.alesno.testtaskispring.common.URL
import com.alesno.testtaskispring.model.response.ResponseJson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET(REQUEST_PARAM)
    fun getResponseAsync(): Deferred<ResponseJson>

    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}