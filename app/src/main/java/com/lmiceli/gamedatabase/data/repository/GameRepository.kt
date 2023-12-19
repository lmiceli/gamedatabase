package com.lmiceli.gamedatabase.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lmiceli.gamedatabase.data.GameMediator
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.data.local.GameDao
import com.lmiceli.gamedatabase.data.remote.dto.request.GameListRequest
import com.lmiceli.gamedatabase.utils.getFromDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepository @ExperimentalPagingApi
@Inject constructor(
    private val mediator: GameMediator,
    private val localDataSource: GameDao
) {

    fun getGame(id: Long) = getFromDB(
        databaseQuery = { localDataSource.getGame(id) },
    )

    @ExperimentalPagingApi
    fun getGames(): Flow<PagingData<Game>> {

        return Pager(
            PagingConfig(
                pageSize = GameListRequest.GAME_REQUEST_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            remoteMediator = mediator,
            pagingSourceFactory = { localDataSource.getAllGames() },

            ).flow

        // todo: I would migrate to flow, but my original architecture was based on livedata and
        //  moving to paging 3 kinda broke too much stuff for me to fix it all in what was supposed
        //  to be a small project
    }
}
