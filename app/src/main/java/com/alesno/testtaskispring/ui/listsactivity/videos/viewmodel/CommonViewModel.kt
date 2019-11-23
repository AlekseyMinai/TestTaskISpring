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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CommonViewModel(
    private val repository: Repository,
    private val videosDao: VideosDao,
    private val objectTransformer: ObjectTransformer
) : ViewModel() {

    var videosObj: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()
    var isProgressBarVisible: ObservableBoolean = ObservableBoolean(false)

    fun onViewListAllMoviesCreated() {
        if (videosObj.isEmpty()) {
            setDataInViewAsyncForTheFirsTime()
        }
    }

    private fun setDataInViewAsyncForTheFirsTime() {
        isProgressBarVisible.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            putVideosObjInListFromDb()

            if (!videosObj.isEmpty()) {
                isProgressBarVisible.set(false)
            }

            val response = getResponseFromServer() ?: return@launch

            videosDao.insertAllVideos(objectTransformer.responseTransformer(response))

            putVideosObjInListFromDb()

            isProgressBarVisible.set(false)
        }
    }

    private suspend fun putVideosObjInListFromDb() {
        val listVideosObj = getAllVideosAsync().await()
        videosObj.clear()
        videosObj.addAll(sortByTitle(listVideosObj))
    }

    private fun getAllVideosAsync(): Deferred<List<VideoObject>> {
        return viewModelScope.async {
            videosDao.getAllVideos()
        }
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

    fun onCheckboxClicked(idVideo: Long, isFavorite: Boolean) {
        changeFavoriteStatus(idVideo, isFavorite)
        //redo it!!!
        val list: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()
        list.addAll(videosObj)
        videosObj.clear()
        videosObj.addAll(list)
    }

    private fun changeFavoriteStatus(idVideo: Long, isFavorite: Boolean) {
        videosObj.map { videoObject ->
            if (videoObject.id == idVideo) videoObject.isFavorite = isFavorite
        }
    }

    private fun sortByTitle(videosObj: List<VideoObject>): List<VideoObject> {
        return videosObj.sortedWith(compareBy { it.title })
    }

    private fun filterByFavoriteVideos(videosObj: List<VideoObject>): List<VideoObject> {
        return videosObj.filter { videoObject -> videoObject.isFavorite }
    }
}