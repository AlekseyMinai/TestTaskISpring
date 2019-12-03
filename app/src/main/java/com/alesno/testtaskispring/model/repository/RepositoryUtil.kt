package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject


fun List<VideoObject>.sortByTitle(): List<VideoObject> {
    return this.sortedWith(compareBy { it.title })
}

fun List<VideoObject>.filterByFavoriteVideos(): List<VideoObject> {
    return this.filter { videoObject -> videoObject.isFavorite }
}

fun findVideoById(videosObj: List<VideoObject>, idVideo: Long): VideoObject? {
    return videosObj.firstOrNull { videoObject -> videoObject.id == idVideo }
}