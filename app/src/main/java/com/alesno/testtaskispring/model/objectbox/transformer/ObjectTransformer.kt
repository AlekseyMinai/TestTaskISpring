package com.alesno.testtaskispring.model.objectbox.transformer

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.response.Response

interface ObjectTransformer {
    fun responseTransformer(response: Response): List<VideoObject>
}