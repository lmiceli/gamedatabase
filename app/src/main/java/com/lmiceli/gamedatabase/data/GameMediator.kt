package com.lmiceli.gamedatabase.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.data.entities.GamePageKey
import com.lmiceli.gamedatabase.data.local.AppDatabase
import com.lmiceli.gamedatabase.data.remote.dto.request.GameListRequest
import com.lmiceli.gamedatabase.data.remote.games.GameService
import com.lmiceli.gamedatabase.data.repository.GameMapper
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class GameMediator(
    private val service: GameService,
    private val database: AppDatabase,
) : RemoteMediator<Int, Game>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Game>
    ): MediatorResult {

        return try {
            val nextPageNumber: Int = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    getNextPageNumber()
                }
            } ?: 1

            val response = service.getAllGames(GameListRequest.getPopularGamesBody(nextPageNumber))

            GameMapper
                .map(response.body())
                .takeIf { it.isNotEmpty() }
                ?.let { games ->

                    database.withTransaction {
                        database.gamePageKeyDao()
                            .savePagingKeys(GamePageKey(0, nextPageNumber))
                        database.gameDao().insertAll(games)
                    }
                }

            MediatorResult.Success(endOfPaginationReached = false)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }

    }

    private suspend fun getNextPageNumber() =
        database.gamePageKeyDao().getGamePageKeys().maxByOrNull {
            it.pageNumber
        }?.pageNumber?.let { it + 1 }

}
