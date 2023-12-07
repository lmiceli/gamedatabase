package com.lmiceli.gamedatabase.data.remote.dto.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyDTO(
    val id: Long,
    val company: NameValueDTO,
)
