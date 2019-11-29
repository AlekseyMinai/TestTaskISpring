package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.domain.VideoCommonDomain
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain

interface Repository {

    suspend fun getListVideos(): List<VideoCommonDomain>

    fun getListFavoriteVideos(): List<VideoCommonDomain>

    suspend fun getListVideoFromServer(): List<VideoCommonDomain>

    suspend fun changeFavoriteStatus(idVideo: Long, isFavorite: Boolean): List<VideoCommonDomain>

    suspend fun getListVideosFromDb(): List<VideoCommonDomain>

    suspend fun getVideoById(videoId: Long): VideoDetailVMDomain

    suspend fun updateVideo(videoDomain: VideoDetailVMDomain)
}