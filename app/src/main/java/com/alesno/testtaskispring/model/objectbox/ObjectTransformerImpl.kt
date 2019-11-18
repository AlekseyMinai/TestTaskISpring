package com.alesno.testtaskispring.model.objectbox

import com.alesno.testtaskispring.model.objectbox.entities.ExpertObject
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.response.Expert
import com.alesno.testtaskispring.model.response.Response
import com.alesno.testtaskispring.model.response.Video

object ObjectTransformerImpl: ObjectTransformer {

    override fun responseTransformer(response: Response): List<VideoObject>{
        return response.videos.map{ video -> videoTransformer(video) }
    }

    private fun expertTransformer(expert: Expert): ExpertObject{
        return ExpertObject(
            expert.avatar,
            expert.firstName,
            expert.id,
            expert.secondName,
            expert.speciality
        )
    }

    private fun listExpertsTransformer(listExperts: List<Expert>): List<ExpertObject>{
        return listExperts.map { expert -> expertTransformer(expert) }
    }

    private fun videoTransformer(video: Video): VideoObject{
        val videoObj: VideoObject = VideoObject(
            video.id,
            video.meta.title,
            video.meta.topics,
            video.meta.url
        )
        videoObj.experts.addAll(listExpertsTransformer(video.experts))
        return videoObj
    }
}