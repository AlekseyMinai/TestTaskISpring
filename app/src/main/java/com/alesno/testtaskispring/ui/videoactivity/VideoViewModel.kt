package com.alesno.testtaskispring.ui.videoactivity

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain.ExpertDetailDomain
import com.alesno.testtaskispring.model.repository.Repository
import kotlinx.coroutines.launch

class VideoViewModel(private val mRepository: Repository) : ViewModel() {

    var isShowingProgressBar: ObservableBoolean = ObservableBoolean(true)
    var isShowingPlayButton: ObservableBoolean = ObservableBoolean(false)
    var topics: ObservableList<String> = ObservableArrayList<String>()
    var experts: ObservableList<ExpertDetailDomain> = ObservableArrayList<ExpertDetailDomain>()
    var videoUrl: ObservableField<String> = ObservableField("")
    var observableVideos: ObservableField<VideoDetailVMDomain> = ObservableField()
    var videoId: Long = 0
    val mPlayVideoLiveData: MutableLiveData<Int> = MutableLiveData()

    fun onViewCreated() {
        getVideo()
    }

    fun onErrorLoadVideo() {
        isShowingPlayButton.set(true)
        isShowingProgressBar.set(false)
    }

    fun setVideoUrl(videoUrl: String) {
        this.videoUrl.set(videoUrl)
    }

    fun onStopPlaybackVideo(progressTime: Int, duration: Int) {
        setProgressInVideoObject(progressTime, duration)
        val videoObject = observableVideos.get() ?: return
        saveVideoInDb(videoObject)
    }

    private fun setProgressInVideoObject(progressTime: Int, duration: Int) {
        val video = observableVideos.get() ?: return
        video.progressTime = progressTime
        video.progress = calculateProgress(progressTime, duration)
        observableVideos.set(video)
    }

    fun setVideoStat(isShowingProgressBar: Boolean) {
        this.isShowingProgressBar.set(isShowingProgressBar)
    }

    private fun getVideo() {
        viewModelScope.launch {
            val video = mRepository.getVideoById(videoId)
            setDataInField(video)
            videoUrl.set(video.url)
            playVideo()
        }
    }

    fun playVideo() {
        mPlayVideoLiveData.value = observableVideos.get()!!.progressTime
        isShowingPlayButton.set(false)
        isShowingProgressBar.set(true)
    }

    private fun setDataInField(video: VideoDetailVMDomain) {
        observableVideos.set(video)
        experts.addAll(video.experts)
        video.topics?.let { topics.addAll(it) }
    }

    private fun calculateProgress(progressTime: Int, duration: Int): Int {
        return (progressTime * 100) / duration
    }

    private fun saveVideoInDb(video: VideoDetailVMDomain) {
        viewModelScope.launch {
            mRepository.updateVideo(video)
        }
    }

}