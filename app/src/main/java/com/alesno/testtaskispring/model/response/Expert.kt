package com.alesno.testtaskispring.model.response

import com.google.gson.annotations.SerializedName

data class Expert(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("second_name")
    val secondName: String,
    @SerializedName("speciality")
    val speciality: String
)