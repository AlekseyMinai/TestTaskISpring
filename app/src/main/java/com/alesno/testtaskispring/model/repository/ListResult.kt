package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.domain.VideoCommonDomain

sealed class ListResult {
    data class Success(val videoCommonDomain: List<VideoCommonDomain>) : ListResult()
    object DataIsMiss : ListResult()
    object ConnectError : ListResult()
}