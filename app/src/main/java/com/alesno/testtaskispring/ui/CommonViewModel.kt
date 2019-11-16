package com.alesno.testtaskispring.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.repository.Repository
import com.alesno.testtaskispring.model.response.Response
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class CommonViewModel(val repository: Repository): ViewModel() {

    /*private val job = SupervisorJob()
    private val scope = CoroutineScope(job + Dispatchers.Main)*/

    fun getResponseAsync(){

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response: Response = repository.getResponseAsync().await()
                Log.d("log", response.videos[0].id )
            }catch (e: Exception){
                Log.d("log", e.toString())
            }
        }

    }

    /*override fun onCleared() {
        super.onCleared()
        //scope.coroutineContext.cancelChildren()
    }*/

}