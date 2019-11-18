package com.alesno.testtaskispring.model.objectbox.entities

import com.alesno.testtaskispring.model.objectbox.entities.convertor.TopicsConverter
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany

data class VideoObj(

    @Id var id: Long = 0,

    @NameInDb("id_response")
    var idResponse: String? = null,

    var title: String? = null,

    @Convert(converter = TopicsConverter::class, dbType = String::class)
    var topics: List<String>? = null,

    var url: String? = null,

    @NameInDb("is_favorite")
    var isFavorite: Boolean = false,

    @NameInDb("progress_time")
    var progressTime: Long = 0
){

    @Backlink(to = "video")
    lateinit var experts: ToMany<ExpertObj>
}