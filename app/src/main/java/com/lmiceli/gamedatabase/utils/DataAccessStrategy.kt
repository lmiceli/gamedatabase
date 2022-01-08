package com.lmiceli.gamedatabase.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.lmiceli.gamedatabase.utils.Resource.*
import kotlinx.coroutines.Dispatchers

@Suppress("USELESS_CAST")
fun <T> getFromDB(
    databaseQuery: () -> LiveData<T>,
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(LOADING())
        val source = databaseQuery.invoke().map { SUCCESS(it) as Resource<T> }
        emitSource(source)
    }
