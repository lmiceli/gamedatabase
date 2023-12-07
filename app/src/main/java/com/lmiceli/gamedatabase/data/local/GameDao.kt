package com.lmiceli.gamedatabase.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lmiceli.gamedatabase.data.entities.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM game ORDER BY totalRatingCount DESC")
    fun getAllGames(): PagingSource<Int, Game>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getGame(id: Long): LiveData<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<Game>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: Game)

    @Query("DELETE FROM game")
    fun deleteAll()

}
