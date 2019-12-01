package com.alesno.testtaskispring.model.domain

data class VideoCommonDomain(
    val id: Long,
    val title: String?,
    val preview: String?,
    val isFavorite: Boolean,
    val progress: Int,
    var url: String?
)