package com.lmiceli.gamedatabase.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_page_key")
data class GamePageKey(
    @PrimaryKey
    val id: Long,
    val pageNumber: Int,
)
