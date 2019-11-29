package com.alesno.testtaskispring.model.domain.transformer

import com.alesno.testtaskispring.model.domain.VideoCommonDomain
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain
import com.alesno.testtaskispring.model.objectbox.entities.ExpertObject
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

fun List<VideoObject>.transformToListVideosCommonDomain(): List<VideoCommonDomain> {
    return this.map { fromDataToDomainCommon(it) }
}

fun fromDataToDomainCommon(videoObject: VideoObject): VideoCommonDomain {
    return VideoCommonDomain(
        videoObject.id,
        videoObject.title,
        videoObject.preview,
        videoObject.isFavorite,
        videoObject.progress,
        videoObject.url
    )
}

fun fromDomainToDataCommon(
    videoDomain: VideoCommonDomain,
    videoObject: VideoObject
): VideoObject {
    videoObject.isFavorite = videoDomain.isFavorite
    return VideoObject()
}

fun fromDataToDomainDetail(videoObject: VideoObject): VideoDetailVMDomain {
    return VideoDetailVMDomain(
        videoObject.id,
        videoObject.title,
        videoObject.url,
        videoObject.progressTime,
        videoObject.progress,
        videoObject.topics,
        fromListDataToDomainListExpertDetail(
            videoObject.experts
        )
    )
}

fun fromDomainToDataDetail(
    videoDomain: VideoDetailVMDomain,
    videoObject: VideoObject
): VideoObject {
    videoObject.progressTime = videoDomain.progressTime
    videoObject.progress = videoDomain.progress
    return videoObject
}

private fun fromDataToDomainExpertDetail(expertObject: ExpertObject): VideoDetailVMDomain.ExpertDetailDomain {
    return VideoDetailVMDomain.ExpertDetailDomain(
        expertObject.avatar,
        expertObject.firstName,
        expertObject.secondName,
        expertObject.speciality
    )
}

private fun fromListDataToDomainListExpertDetail(expertsObject: List<ExpertObject>): List<VideoDetailVMDomain.ExpertDetailDomain> {
    return expertsObject.map { expertObject ->
        fromDataToDomainExpertDetail(
            expertObject
        )
    }
}
