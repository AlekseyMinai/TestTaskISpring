package com.alesno.testtaskispring.model.objectbox.dao

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

interface VideosDao {

    fun getAllVideos(): List<VideoObject>

    fun insertAllVideos(videosFromServer: List<VideoObject>): Boolean

    fun getVideoById(idVideo: Long): VideoObject

    fun updateVideo(videoObject: VideoObject)

}