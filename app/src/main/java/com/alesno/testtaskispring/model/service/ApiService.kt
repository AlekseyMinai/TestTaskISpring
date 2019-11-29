package com.alesno.testtaskispring.model.service

import com.alesno.testtaskispring.common.REQUEST_PARAM
import com.alesno.testtaskispring.common.URL
import com.alesno.testtaskispring.model.response.ResponseJson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET(REQUEST_PARAM)
    suspend fun getResponseAsync(): ResponseJson

    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}