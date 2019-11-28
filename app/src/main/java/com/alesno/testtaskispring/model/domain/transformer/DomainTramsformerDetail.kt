package com.alesno.testtaskispring.model.domain.transformer

import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain.ExpertDetailDomain
import com.alesno.testtaskispring.model.domain.transformer.base.BaseDomainTransformer
import com.alesno.testtaskispring.model.objectbox.entities.ExpertObject
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

class DomainTramsformerDetail : BaseDomainTransformer<VideoDetailVMDomain>() {

    override fun fromDataToDomain(videoObject: VideoObject): VideoDetailVMDomain {
        return VideoDetailVMDomain(
            videoObject.id,
            videoObject.title,
            videoObject.url,
            videoObject.progressTime,
            videoObject.progress,
            videoObject.topics,
            fromListDataToDomainList(videoObject.experts)
        )
    }

    override fun fromDomainToData(
        videoDomain: VideoDetailVMDomain,
        videoObject: VideoObject
    ): VideoObject {
        videoObject.progressTime = videoDomain.progressTime
        videoObject.progress = videoDomain.progress
        return videoObject
    }

    private fun fromDataToDomainExpert(expertObject: ExpertObject): ExpertDetailDomain {
        return ExpertDetailDomain(
            expertObject.avatar,
            expertObject.firstName,
            expertObject.secondName,
            expertObject.speciality
        )
    }

    private fun fromListDataToDomainList(expertsObject: List<ExpertObject>): List<ExpertDetailDomain> {
        return expertsObject.map { expertObject -> fromDataToDomainExpert(expertObject) }
    }
}