package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import kotlinx.coroutines.CoroutineScope

interface Repository {

    suspend fun getListVideosObject(scope: CoroutineScope): List<VideoObject>

    suspend fun updateListFromServer(): List<VideoObject>

    suspend fun changeFavoriteStatus(idVideo: Long, isFavorite: Boolean): MutableList<VideoObject>

    fun getVideoById(videoId: Long): VideoObject

    fun updateVideo(videoObj: VideoObject)

    suspend fun getListVideosObjFromDb(scope: CoroutineScope): MutableList<VideoObject>
}