package com.lmiceli.gamedatabase.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.lmiceli.gamedatabase.data.GameMediator
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.data.local.GameDao
import com.lmiceli.gamedatabase.data.remote.dto.request.GameListRequest
import com.lmiceli.gamedatabase.utils.getFromDB
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
    fun getGames(): LiveData<PagingData<Game>> {

        return Pager(
            PagingConfig(
                pageSize = GameListRequest.GAME_REQUEST_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            remoteMediator = mediator,
            pagingSourceFactory = { localDataSource.getAllGames() },

            ).liveData

        // todo: I would migrate to flow, but my original architecture was based on livedata and
        //  moving to paging 3 kinda broke too much stuff for me to fix it all in what was supposed
        //  to be a small project
    }
}
