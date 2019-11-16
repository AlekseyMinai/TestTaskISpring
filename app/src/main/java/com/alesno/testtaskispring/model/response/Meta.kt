package com.alesno.testtaskispring.model.response

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("title")
    val title: String,
    @SerializedName("topics")
    val topics: List<String>,
    @SerializedName("url")
    val url: String
)