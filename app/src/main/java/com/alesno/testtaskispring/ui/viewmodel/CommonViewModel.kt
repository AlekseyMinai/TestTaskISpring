package com.alesno.testtaskispring.ui.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import com.alesno.testtaskispring.model.repository.Repository
import com.alesno.testtaskispring.model.response.Response
import com.alesno.testtaskispring.model.response.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommonViewModel(
            private val repository: Repository,
            private val videosDao: VideosDao,
            private val objectTransformer: ObjectTransformer
): ViewModel() {

    var videos: ObservableList<Video> = ObservableArrayList<Video>()
    var videosObj: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()

    fun onViewCreated() {
        setDataInViewAsync()
    }

    private fun setDataInViewAsync(){
        viewModelScope.launch(Dispatchers.Main) {
            var listVideoObjects = getVideosAsync()
            videosObj.addAll(listVideoObjects)

            val response = getResponseAsync()
            videosDao.insertAllVideos(objectTransformer.responseTransformer(response))

            listVideoObjects = getVideosAsync()
            videosObj.addAll(listVideoObjects)
        }
    }

    private suspend fun getVideosAsync():List<VideoObject>{
        var listVideoObjects: List<VideoObject> = listOf()
        try{
            listVideoObjects = videosDao.getAllVideos()
        }catch (e: java.lang.Exception){
            Log.d("log", e.toString())
        }
        return listVideoObjects
    }

    private suspend fun getResponseAsync():Response{
        var response: Response? = null
        try {
            response = repository.getResponseAsync().await()
        } catch (e: Exception) {
            Log.d("log", e.toString())
        }
        return response!!
    }
}