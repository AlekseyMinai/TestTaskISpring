package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

fun sortByTitle(videosObj: List<VideoObject>): List<VideoObject> {
    return videosObj.sortedWith(compareBy { it.title })
}

fun filterByFavoriteVideos(videosObj: List<VideoObject>): List<VideoObject> {
    return videosObj.filter { videoObject -> videoObject.isFavorite }
}

fun findVideoById(videosObj: List<VideoObject>, idVideo: Long): VideoObject? {
    return videosObj.firstOrNull { videoObject -> videoObject.id == idVideo }
}