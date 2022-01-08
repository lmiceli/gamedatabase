package com.lmiceli.gamedatabase.data.remote.dto.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameDTO(
    val id: Long,
    val name: String,
    val summary: String?,
    val cover: CoverDTO?,
    val genres: List<NameValueDTO>?,
    val platforms: List<NameValueDTO>,
    val involved_companies: List<CompanyDTO>?,
    val total_rating_count: Int,
    val aggregated_rating: Double,
    val first_release_date: Long,
)
