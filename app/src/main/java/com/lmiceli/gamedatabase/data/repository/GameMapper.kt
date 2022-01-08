package com.lmiceli.gamedatabase.data.repository

import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.data.remote.dto.response.GameDTO

object GameMapper {

    fun map(dtos: List<GameDTO>?): List<Game> =
        dtos?.map { dto ->
            map(dto)
        } ?: listOf()

    private fun map(dto: GameDTO) = Game(
        id = dto.id,
        name = dto.name,
        summary = dto.summary,
        coverUrl = dto.cover?.url,
        totalRatingCount = dto.total_rating_count,
        aggregatedRating = dto.aggregated_rating.toInt(),
        genre = dto.genres?.joinToString { it.name },
        companies = dto.involved_companies?.joinToString { it.company.name },
        platforms = dto.platforms.joinToString { it.name },
        published = dto.first_release_date,
    )

}
