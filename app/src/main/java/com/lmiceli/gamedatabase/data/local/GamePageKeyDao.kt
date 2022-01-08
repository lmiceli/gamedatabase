package com.lmiceli.gamedatabase.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lmiceli.gamedatabase.data.entities.GamePageKey

@Dao
interface GamePageKeyDao {

    @Insert(onConflict = REPLACE)
    suspend fun savePagingKeys(gamePageKey: GamePageKey)

    @Query("SELECT * FROM game_page_key ORDER BY id DESC")
    suspend fun getGamePageKeys(): List<GamePageKey>

}
