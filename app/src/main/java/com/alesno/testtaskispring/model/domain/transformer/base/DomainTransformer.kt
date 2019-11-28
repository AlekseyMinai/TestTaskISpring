package com.alesno.testtaskispring.model.domain.transformer.base

import com.alesno.testtaskispring.model.domain.VideoDomain
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

interface DomainTransformer<V : VideoDomain> {
    fun fromDataToDomain(videoObject: VideoObject): V
    fun fromDomainToData(videoDomain: V, videoObject: VideoObject): VideoObject
    fun fromListDataToDomain(videosObject: List<VideoObject>): List<V>
}