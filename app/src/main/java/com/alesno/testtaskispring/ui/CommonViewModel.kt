package com.alesno.testtaskispring.ui

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.objectbox.entity.VideoObj
import com.alesno.testtaskispring.model.repository.Repository
import com.alesno.testtaskispring.model.response.Response
import com.alesno.testtaskispring.model.response.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommonViewModel(val repository: Repository): ViewModel() {

    var videos: ObservableList<Video> = ObservableArrayList<Video>()
    var videosObj: ObservableList<VideoObj> = ObservableArrayList<VideoObj>()


    fun getResponseAsync(){
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response: Response = repository.getResponseAsync().await()
                Log.d("log", response.videos[0].id )
                videos.addAll(response.videos)
            }catch (e: Exception){
                Log.d("log", e.toString())
            }
        }

    }
}