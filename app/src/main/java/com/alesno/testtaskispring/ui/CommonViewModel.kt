package com.alesno.testtaskispring.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alesno.testtaskispring.model.repository.Repository
import com.alesno.testtaskispring.model.response.Response
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class CommonViewModel(val repository: Repository): ViewModel() {

    private val job = Job()

    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    fun getResponseAsync(){

        scope.launch {
            try {
                val deferred: Deferred<Response> = repository.getResponseAsync()
                val response: Response = deferred.await()
                Log.d("log", response.videos[0].id )
            }catch (e: Exception){
                Log.d("log", e.toString())
            }
        }

    }

}