package com.lmiceli.gamedatabase.ui.gamedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.data.repository.GameRepository
import com.lmiceli.gamedatabase.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    private val _id = MutableLiveData<Long>()

    private val _game = _id.switchMap { id ->
        repository.getGame(id)
    }
    val game: LiveData<Resource<Game>> = _game

    fun start(id: Long) {
        _id.value = id
    }
}
