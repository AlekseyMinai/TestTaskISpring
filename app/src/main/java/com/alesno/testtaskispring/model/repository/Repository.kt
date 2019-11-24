package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import kotlinx.coroutines.CoroutineScope

interface Repository {

    suspend fun getListVideosObject(scope: CoroutineScope): List<VideoObject>

    fun updateListFromServer(scope: CoroutineScope): List<VideoObject>

    fun changeFavoriteStatus(idVideo: Long, isFavorite: Boolean): MutableList<VideoObject>

    fun filterByFavoriteVideos(): List<VideoObject>
}