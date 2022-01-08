package com.lmiceli.gamedatabase.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @PrimaryKey
    val id: Long,
    val name: String,
    val summary: String?,
    val coverUrl: String?,
    val totalRatingCount: Int,
    val aggregatedRating: Int,
    val genre: String?,
    val companies: String?,
    val platforms: String,
    val published: Long?,
)

