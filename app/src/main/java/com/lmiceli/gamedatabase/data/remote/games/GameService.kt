package com.lmiceli.gamedatabase.data.remote.games

import com.lmiceli.gamedatabase.data.remote.dto.response.GameDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GameService {

    @POST("games")
    suspend fun getAllGames(@Body body: String): Response<List<GameDTO>>

}
