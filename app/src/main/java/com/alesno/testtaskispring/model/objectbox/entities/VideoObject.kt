package com.alesno.testtaskispring.model.objectbox.entities

import com.alesno.testtaskispring.model.objectbox.entities.convertor.TopicsConverter
import io.objectbox.annotation.*
import io.objectbox.relation.ToMany

@Entity
data class VideoObject(

    @NameInDb("id_response")
    var idResponse: String? = null,

    var title: String? = null,

    @Convert(converter = TopicsConverter::class, dbType = String::class)
    var topics: List<String>? = null,

    var url: String? = null,

    val preview: String? = null,

    @NameInDb("is_favorite")
    var isFavorite: Boolean = false,

    @NameInDb("progress_time")
    var progressTime: Long = 0,

    @Id var id: Long = 0
){
    @Backlink(to = "video")
    lateinit var experts: ToMany<ExpertObject>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoObject

        if (idResponse != other.idResponse) return false

        return true
    }

    override fun hashCode(): Int {
        return idResponse?.hashCode() ?: 0
    }


}