package com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.repository.Repository
import com.alesno.testtaskispring.model.repository.filterByFavoriteVideos
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
                isProgressBarVisible.set(true)
                setDataInListAllVideosFragment()
                setDataInFavoriteListFragment()
                isProgressBarVisible.set(false)
            }
        }
    }

    fun onViewResumed() {
        viewModelScope.launch {
            val videosObjFromDb = repository.getListVideosObjFromDb()
            videosObj.clear()
            videosObj.addAll(videosObjFromDb)
        }
    }

    fun onRefreshedListAllVideos() {
        isProgressBarVisible.set(true)
        viewModelScope.launch {
            val videosObject = repository.updateListFromServer()
            videosObj.clear()
            videosObj.addAll(videosObject)
            isRefreshedLiveData.postValue(false)
            isProgressBarVisible.set(false)
        }
    }

    fun onCheckboxClicked(idVideo: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            val updatedVideosObj = repository.changeFavoriteStatus(idVideo, isFavorite)
            videosObj.clear()
            videosObj.addAll(updatedVideosObj)
            setDataInFavoriteListFragment()
        }
    }

    fun setDataInFavoriteListFragment() {
        favoriteVideosObj.clear()
        favoriteVideosObj.addAll(filterByFavoriteVideos(videosObj))
    }

    private suspend fun setDataInListAllVideosFragment() {
        val videosObj = repository.getListVideosObject()
        this.videosObj.addAll(videosObj)
    }

}