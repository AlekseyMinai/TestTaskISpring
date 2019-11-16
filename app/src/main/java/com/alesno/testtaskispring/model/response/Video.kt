package com.alesno.testtaskispring.model.response

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("experts")
    val experts: List<Expert>,
    @SerializedName("id")
    val id: String,
    @SerializedName("meta")
    val meta: Meta
)