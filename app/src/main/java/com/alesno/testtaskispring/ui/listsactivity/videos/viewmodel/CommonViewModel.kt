package com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.repository.Repository
import kotlinx.coroutines.launch

class CommonViewModel(
    private val repository: Repository
) : ViewModel() {

    var videosObj: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()
    var favoriteVideosObj: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()
    var isProgressBarVisible: ObservableBoolean = ObservableBoolean(false)

    fun onViewListAllMoviesCreated() {
        if (videosObj.isEmpty())
            viewModelScope.launch { setDataInViewAsyncForTheFirsTime() }
    }

    fun onListFavoriteVideosCreated() {
        setDataInFavoriteListFragment()
    }

    fun onRefreshedListAllVideos() {
        val videosObj = repository.updateListFromServer(viewModelScope)
        this.videosObj.clear()
        this.videosObj.addAll(videosObj)
    }

    private fun setDataInFavoriteListFragment() {
        favoriteVideosObj.clear()
        filterByFavoriteVideos(videosObj).forEach { videoObj -> favoriteVideosObj.add(videoObj) }
    }

    private suspend fun setDataInViewAsyncForTheFirsTime() {
        isProgressBarVisible.set(true)
        val videosObj = repository.getListVideosObject(viewModelScope)
        this.videosObj.addAll(videosObj)
        isProgressBarVisible.set(false)
    }

    fun onCheckboxClicked(idVideo: Long, isFavorite: Boolean) {
        changeFavoriteStatus(idVideo, isFavorite)
        //redo it!!!
        val list: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()
        list.addAll(videosObj)
        videosObj.clear()
        videosObj.addAll(list)
        setDataInFavoriteListFragment()
    }

    private fun changeFavoriteStatus(idVideo: Long, isFavorite: Boolean) {
        videosObj.map { videoObject ->
            if (videoObject.id == idVideo) videoObject.isFavorite = isFavorite
        }
    }

    private fun filterByFavoriteVideos(videosObj: List<VideoObject>): List<VideoObject> {
        return videosObj.filter { videoObject -> videoObject.isFavorite }
    }

}