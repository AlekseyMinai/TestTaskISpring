package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.domain.VideoCommonDomain

sealed class ListResult {
    data class OldData(val videoCommonDomain: List<VideoCommonDomain>) : ListResult()
    data class NewData(val videoCommonDomain: List<VideoCommonDomain>) : ListResult()
    data class Favorite(val videoCommonDomain: List<VideoCommonDomain>) : ListResult()
    object DataIsMiss : ListResult()
    object ConnectError : ListResult()
}