package com.alesno.testtaskispring.model.domain

data class VideoDetailVMDomain(
    val id: Long,
    val title: String?,
    val url: String?,
    val progressTime: Int,
    val progress: Int,
    val topics: List<String>?,
    val experts: List<ExpertDetailDomain>
) : VideoDomain() {
    data class ExpertDetailDomain(
        val avatar: String?,
        val firstName: String?,
        val secondName: String?,
        val speciality: String?
    )
}