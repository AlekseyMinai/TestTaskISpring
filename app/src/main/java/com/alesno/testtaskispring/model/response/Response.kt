package com.alesno.testtaskispring.model.response
import com.google.gson.annotations.SerializedName


data class Response(
   @SerializedName("list")
    val videos: List<Video>
)

