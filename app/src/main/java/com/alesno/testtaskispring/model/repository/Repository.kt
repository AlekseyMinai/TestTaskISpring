package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

interface Repository {

    suspend fun getListVideosObject(): List<VideoObject>

    suspend fun updateListFromServer(): List<VideoObject>

    suspend fun changeFavoriteStatus(idVideo: Long, isFavorite: Boolean): List<VideoObject>

    suspend fun getVideoById(videoId: Long): VideoObject

    suspend fun updateVideo(videoObj: VideoObject)

    suspend fun getListVideosObjFromDb(): List<VideoObject>
}