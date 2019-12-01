package com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.domain.VideoCommonDomain
import com.alesno.testtaskispring.model.repository.ListResult
import com.alesno.testtaskispring.model.repository.Repository
import com.alesno.testtaskispring.ui.common.SingleLiveEvent
import kotlinx.coroutines.launch

class CommonViewModel(
    private val repository: Repository
) : ViewModel() {

    var videosObj: ObservableList<VideoCommonDomain> = ObservableArrayList<VideoCommonDomain>()
    var favoriteVideosObj: ObservableList<VideoCommonDomain> =
        ObservableArrayList<VideoCommonDomain>()
    var isProgressBarVisible: ObservableBoolean = ObservableBoolean(false)
    var isRefreshedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var snackBarEventListener = SingleLiveEvent<String>()
    private var isFirstStart: Boolean = true

    fun onViewResumed() {
        viewModelScope.launch {
            if (isFirstStart) {
                isProgressBarVisible.set(true)
                setDataInVideosOrShowError(repository.getListVideos())
                setDataInFavoriteVideos(repository.getListFavoriteVideos())
                isProgressBarVisible.set(false)
                isFirstStart = false
            }
        }
    }

    fun onActivityRestart() {
        viewModelScope.launch {
            setDataInVideosOrShowError(repository.getListVideosFromDb())
            setDataInFavoriteVideos(repository.getListFavoriteVideos())
        }
    }

    fun onRefreshedListAllVideos() {
        isProgressBarVisible.set(true)
        viewModelScope.launch {
            val videosObject = repository.getListVideoFromServer()
            setDataInVideosOrShowError(videosObject)
            setDataInFavoriteVideos(repository.getListFavoriteVideos())
            isRefreshedLiveData.postValue(false)
            isProgressBarVisible.set(false)
        }
    }

    fun onCheckboxClicked(idVideo: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            val updatedVideosObj = repository.changeFavoriteStatus(idVideo, isFavorite)
            favoriteVideosObj.clear()
            setDataInVideosOrShowError(updatedVideosObj)
            setDataInFavoriteVideos(repository.getListFavoriteVideos())
        }
    }

    private fun setDataInVideosOrShowError(result: ListResult) {
        when (result) {
            is ListResult.Success -> {
                videosObj.clear()
                videosObj.addAll(result.videoCommonDomain)
            }
            is ListResult.ConnectError -> snackBarEventListener.setValue("Connection error")
            is ListResult.DataIsMiss -> snackBarEventListener.setValue("Data is miss")
        }
    }

    private fun setDataInFavoriteVideos(result: ListResult) {
        when (result) {
            is ListResult.Success -> {
                favoriteVideosObj.clear()
                favoriteVideosObj.addAll(result.videoCommonDomain)
            }
        }
    }

}