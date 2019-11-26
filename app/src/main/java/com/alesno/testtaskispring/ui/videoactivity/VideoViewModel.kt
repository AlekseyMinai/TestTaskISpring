package com.alesno.testtaskispring.ui.videoactivity

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.objectbox.entities.ExpertObject
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.repository.Repository
import kotlinx.coroutines.*

class VideoViewModel(private val mRepository: Repository) : ViewModel() {

    var isShowingProgressBar: ObservableBoolean = ObservableBoolean(true)
    var topics: ObservableList<String> = ObservableArrayList<String>()
    var experts: ObservableList<ExpertObject> = ObservableArrayList<ExpertObject>()
    var videoUrl: ObservableField<String> = ObservableField()
    var observableVideosObject: ObservableField<VideoObject> = ObservableField()
    var videoId: Long = 0
    val mPlayVideoLiveData: MutableLiveData<Int> = MutableLiveData()

    fun onViewCreated() {
        getVideo()
    }

    fun setVideoUrl(videoUrl: String) {
        this.videoUrl.set(videoUrl)
    }

    fun onStopPlaybackVideo(progressTime: Int, duration: Int) {
        setProgressInVideoObject(progressTime, duration)
        val videoObject = observableVideosObject.get() ?: return
        saveVideoInDb(videoObject)
    }

    fun setProgressInVideoObject(progressTime: Int, duration: Int) {
        val videoObject = observableVideosObject.get() ?: return
        videoObject.progressTime = progressTime
        videoObject.progress = calculateProgress(progressTime, duration)
        observableVideosObject.set(videoObject)
    }

    fun setVideoStat(isShowingProgressBar: Boolean) {
        this.isShowingProgressBar.set(isShowingProgressBar)
    }

    private fun getVideo() {
        viewModelScope.launch(Dispatchers.IO) {
            val videoObject = getVideoByIdAsync(videoId).await()
            setDataInField(videoObject)
            withContext(Dispatchers.Main) {
                mPlayVideoLiveData.value = videoObject.progressTime
            }
        }
    }

    private fun getVideoByIdAsync(videoId: Long): Deferred<VideoObject> {
        return viewModelScope.async {
            mRepository.getVideoById(videoId)
        }
    }

    private fun setDataInField(videoObject: VideoObject) {
        observableVideosObject.set(videoObject)
        experts.addAll(videoObject.experts)
        videoObject.topics?.let { topics.addAll(it) }
    }

    private fun calculateProgress(progressTime: Int, duration: Int): Int {
        return (progressTime * 100) / duration
    }

    private fun saveVideoInDb(videoObject: VideoObject) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                mRepository.updateVideo(videoObject)
            }
        }
    }

}