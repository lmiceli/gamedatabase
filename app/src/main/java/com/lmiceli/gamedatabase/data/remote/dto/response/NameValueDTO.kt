package com.lmiceli.gamedatabase.data.remote.dto.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NameValueDTO(
    val id: Long,
    val name: String,
)
