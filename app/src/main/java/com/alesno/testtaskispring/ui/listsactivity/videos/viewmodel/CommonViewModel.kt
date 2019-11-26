package com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommonViewModel(
    private val repository: Repository
) : ViewModel() {

    var videosObj: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()
    var favoriteVideosObj: ObservableList<VideoObject> = ObservableArrayList<VideoObject>()
    var isProgressBarVisible: ObservableBoolean = ObservableBoolean(false)
    var isRefreshedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun onViewListAllMoviesCreated() {
        if (videosObj.isEmpty()) {
            viewModelScope.launch {
                setDataInListAllVideosFragment()
                setDataInFavoriteListFragment()
            }
        }
    }

    fun onViewResumed() {
        viewModelScope.launch {
            val videosObjFromDb = repository.getListVideosObjFromDb(viewModelScope)
            videosObj.clear()
            videosObj.addAll(videosObjFromDb)
        }
    }

    fun onRefreshedListAllVideos() {
        isProgressBarVisible.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            val videosObject = repository.updateListFromServer()
            videosObj.clear()
            videosObj.addAll(videosObject)
            isRefreshedLiveData.postValue(false)
            isProgressBarVisible.set(false)
        }
    }

    private suspend fun setDataInListAllVideosFragment() {
        isProgressBarVisible.set(true)
        val videosObj = repository.getListVideosObject(viewModelScope)
        this.videosObj.addAll(videosObj)
        isProgressBarVisible.set(false)
    }

    fun onCheckboxClicked(idVideo: Long, isFavorite: Boolean) {
        val updatedVideosObj = repository.changeFavoriteStatus(idVideo, isFavorite)
        videosObj.clear()
        videosObj.addAll(updatedVideosObj)
        setDataInFavoriteListFragment()
    }

    private fun setDataInFavoriteListFragment() {
        favoriteVideosObj.clear()
        repository.filterByFavoriteVideos()
            .forEach { videoObj -> favoriteVideosObj.add(videoObj) }
    }

}