package com.lmiceli.gamedatabase.data.remote.dto.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoverDTO(
    val id: Long,
    val url: String,
)
