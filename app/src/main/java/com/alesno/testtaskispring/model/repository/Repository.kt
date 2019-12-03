package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain

interface Repository {

    suspend fun getListVideos(): ListResult

    fun getListFavoriteVideos(): ListResult

    suspend fun getListVideoFromServer(): ListResult

    suspend fun changeFavoriteStatus(idVideo: Long, isFavorite: Boolean): ListResult

    suspend fun getListResultFromDb(): ListResult

    suspend fun getVideoById(videoId: Long): VideoDetailVMDomain

    suspend fun updateVideo(videoDomain: VideoDetailVMDomain)
}