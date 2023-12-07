package com.lmiceli.gamedatabase.ui.games

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.lmiceli.gamedatabase.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    repository: GameRepository
) : ViewModel() {

    @ExperimentalPagingApi
    val games = repository.getGames().cachedIn(this)

}
