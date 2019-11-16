package com.alesno.testtaskispring.model.objectbox

import com.alesno.testtaskispring.model.response.Expert
import com.alesno.testtaskispring.model.response.Meta
import com.alesno.testtaskispring.model.response.Response
import com.alesno.testtaskispring.model.response.Video

class Transformator() {

    fun transformFromResponseToVideoObj(video: Video): VideoObj{
        return VideoObj(
            transformFromListExpertToListExpertObj(video.experts),
            video.id,
            transformFromMetaToMetaObj(video.meta)
        )
    }

    private fun transformFromMetaToMetaObj(meta: Meta): MetaObj{
        return MetaObj(meta.title, meta.topics, meta.url)
    }

    private fun transformFromListExpertToListExpertObj(experts: List<Expert>): MutableList<ExpertObj>{
        val expertsObj: MutableList<ExpertObj> = ArrayList()
        for(i in experts.indices){
            expertsObj.add(transformFromExpertToExpertObj(experts[i]))
        }
        return expertsObj
    }

    private fun transformFromExpertToExpertObj(expert: Expert): ExpertObj{
        return ExpertObj(expert.avatar, expert.firstName, expert.id, expert.secondName, expert.speciality)
    }
}