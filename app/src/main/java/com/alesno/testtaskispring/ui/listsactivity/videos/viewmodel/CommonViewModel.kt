package com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.domain.VideoCommonDomain
import com.alesno.testtaskispring.model.repository.Repository
import kotlinx.coroutines.launch

class CommonViewModel(
    private val repository: Repository
) : ViewModel() {

    var videosObj: ObservableList<VideoCommonDomain> = ObservableArrayList<VideoCommonDomain>()
    var favoriteVideosObj: ObservableList<VideoCommonDomain> =
        ObservableArrayList<VideoCommonDomain>()
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
            val videosObjFromDb = repository.getListVideosFromDb()
            videosObj.clear()
            videosObj.addAll(videosObjFromDb)
        }
    }

    fun onRefreshedListAllVideos() {
        isProgressBarVisible.set(true)
        viewModelScope.launch {
            val videosObject = repository.getListVideoFromServer()
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
        favoriteVideosObj.addAll(repository.getListFavoriteVideos())
    }

    private suspend fun setDataInListAllVideosFragment() {
        val videosObj = repository.getListVideos()
        this.videosObj.addAll(videosObj)
    }

}