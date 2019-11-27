package com.alesno.testtaskispring.model.objectbox.dao

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

interface VideosDao {

    fun getAllVideos(): List<VideoObject>

    fun insertAllVideos(videos: List<VideoObject>)

    fun getVideoById(idVideo: Long): VideoObject

    fun updateVideo(videoObject: VideoObject?)

}