package com.alesno.testtaskispring.model.response

import com.google.gson.annotations.SerializedName


data class ResponseJson(
    @SerializedName("videos")
    val videos: List<VideoJson>
)

