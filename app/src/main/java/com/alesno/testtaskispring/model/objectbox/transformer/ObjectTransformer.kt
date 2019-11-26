package com.alesno.testtaskispring.model.objectbox.transformer

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.response.ResponseJson

interface ObjectTransformer {
    fun responseTransformer(response: ResponseJson): List<VideoObject>
}