package com.alesno.testtaskispring.model.response

import com.google.gson.annotations.SerializedName

data class VideoJson(
    @SerializedName("experts")
    val experts: List<ExpertJson>,
    @SerializedName("id")
    val id: String,
    @SerializedName("meta")
    val meta: MetaJson
)