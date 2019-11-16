package com.alesno.testtaskispring.model.objectbox

data class VideoObj(
    val experts: List<ExpertObj>,
    val idResponse: String,
    val meta: MetaObj,
    var id: Long = 0,
    var isFavorite: Boolean = false,
    var progressTime: Long = 0
)