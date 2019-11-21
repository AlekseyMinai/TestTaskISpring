package com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import com.alesno.testtaskispring.model.repository.Repository
import com.alesno.testtaskispring.model.response.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommonViewModel(
            private val repository: Repository,
            private val videosDao: VideosDao,
            private val objectTransformer: ObjectTransformer
): ViewModel() {

    var videosObj: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()
    var isProgressBarVisible: ObservableBoolean = ObservableBoolean(false)

    fun onViewCreated() {
        if(videosObj.isEmpty()){
            setDataInViewAsyncForTheFirsTime()
        }
    }

    private fun setDataInViewAsyncForTheFirsTime(){
        isProgressBarVisible.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            putVideosObjInListFromDb()

            isProgressBarVisible.set(false)

            val response = getResponseFromServer() ?: return@launch

            videosDao.insertAllVideos(objectTransformer.responseTransformer(response))

            putVideosObjInListFromDb()
        }
    }

    private fun putVideosObjInListFromDb(){
        val listVideosObj = videosDao.getAllVideos()
        videosObj.clear()
        videosObj.addAll(sortByTitle(listVideosObj))
    }

    private suspend fun getResponseFromServer(): Response? {
        var response: Response? = null
        try {
            response = repository.getResponseAsync().await()
        } catch (e: Exception) {
            Log.d("log", e.toString())
        }
        return response
    }

    private fun sortByTitle(videosObj: List<VideoObject>): List<VideoObject>{
        return videosObj.sortedWith(compareBy { it.title })
    }

    private fun filterByFavoriteVideos(videosObj: List<VideoObject>): List<VideoObject> {
        return videosObj.filter { videoObject -> videoObject.isFavorite }
    }
}