package com.alesno.testtaskispring.model.domain.transformer

import com.alesno.testtaskispring.model.domain.VideoCommonDomain
import com.alesno.testtaskispring.model.domain.transformer.base.BaseDomainTransformer
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

class DomainTransformerCommon : BaseDomainTransformer<VideoCommonDomain>() {

    override fun fromDataToDomain(videoObject: VideoObject): VideoCommonDomain {
        return VideoCommonDomain(
            videoObject.id,
            videoObject.title,
            videoObject.preview,
            videoObject.isFavorite,
            videoObject.progress
        )
    }

    override fun fromDomainToData(
        videoDomain: VideoCommonDomain,
        videoObject: VideoObject
    ): VideoObject {
        videoObject.isFavorite = videoDomain.isFavorite
        return VideoObject()
    }

}