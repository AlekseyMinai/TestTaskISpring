package com.alesno.testtaskispring.model.domain.transformer.base

import com.alesno.testtaskispring.model.domain.VideoDomain
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

interface DomainTransformer<V : VideoDomain> {
    fun fromDataToDomain(videoObject: VideoObject): V
    fun fromDomainToData(videoDomain: V, videoObject: VideoObject): VideoObject
    //fun <V> List<VideoObject>.fromListDataToDomain(fromDataToDomain: (VideoObject) -> V): List<V>
}

fun <V : VideoDomain, T : DomainTransformer<V>> List<VideoObject>.fromListDataToDomain(
    transformer: T
): List<V> {
    val videosDomain: MutableList<V> = mutableListOf()
    for (item in this)
        videosDomain.add(transformer.fromDataToDomain(item))
    return videosDomain
}
