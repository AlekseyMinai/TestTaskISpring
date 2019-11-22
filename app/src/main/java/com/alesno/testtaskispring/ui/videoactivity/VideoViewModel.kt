package com.alesno.testtaskispring.ui.videoactivity

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.entities.ExpertObject
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class VideoViewModel(val videosDao: VideosDao, val objectTransformer: ObjectTransformer): ViewModel() {

    var topics: ObservableList<String> = ObservableArrayList<String>()
    var experts: ObservableList<ExpertObject> = ObservableArrayList<ExpertObject>()
    var videoUrl: ObservableField<String> = ObservableField()
    var observableVideosObject: ObservableField<VideoObject> = ObservableField()
    var videoId: Long = 0

    fun onViewCreated() {
        videoUrl.set(" ")
        if(observableVideosObject.get() != null){
            return
        }
        getVideo()

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
        videoUrl.set(videoObject.url)
        videoObject.topics?.let { topics.addAll(it) }
    }

    private fun getVideoByIdAsync(videoId: Long): Deferred<VideoObject>{
        return viewModelScope.async{
            videosDao.getVideoById(videoId)
        }
    }
}