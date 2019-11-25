package com.alesno.testtaskispring.ui.videoactivity

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.common.PlayerStartInfo
import com.alesno.testtaskispring.model.objectbox.entities.ExpertObject
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.repository.Repository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class VideoViewModel(val repository: Repository): ViewModel() {

    var isShowingProgressBar: ObservableBoolean = ObservableBoolean(true)
    var topics: ObservableList<String> = ObservableArrayList<String>()
    var experts: ObservableList<ExpertObject> = ObservableArrayList<ExpertObject>()
    var videoUrl: ObservableField<String> = ObservableField()
    var observableVideosObject: ObservableField<VideoObject> = ObservableField()
    var videoId: Long = 0
    private var playerStartInfo: PlayerStartInfo = PlayerStartInfo(false, 0)
    val playVideoLiveData: MutableLiveData<PlayerStartInfo> = MutableLiveData()

    fun onViewCreated() {
        playVideoLiveData.value = playerStartInfo
        if(observableVideosObject.get() != null){
            return
        }
        getVideo()
    }

    fun setVideoUrl(videoUrl: String){
        this.videoUrl.set(videoUrl)
    }

    private fun getVideo(){
        viewModelScope.launch (Dispatchers.IO){
            val videoObject = getVideoByIdAsync(videoId).await()
            setDataInField(videoObject)
        }
    }

    private fun setDataInField(videoObject: VideoObject) {
        observableVideosObject.set(videoObject)
        experts.addAll(videoObject.experts)
        videoObject.topics?.let { topics.addAll(it) }
    }

    private fun getVideoByIdAsync(videoId: Long): Deferred<VideoObject>{
        return viewModelScope.async{
            repository.getVideoById(videoId)
        }
    }

    fun onPausePlaybackVideo(progressTime: Int){
        playerStartInfo.isVideoStarted = true
        playerStartInfo.progressTime = progressTime

        //val videoObject = observableVideosObject[0]
        //videoObject!!.progressTime = progressTime
        //observableVideosObject.set(videoObject)
    }

    fun setVideoStat(isShowingProgressBar: Boolean) {
        this.isShowingProgressBar.set(isShowingProgressBar)
    }
}