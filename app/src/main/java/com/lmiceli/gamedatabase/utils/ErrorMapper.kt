package com.lmiceli.gamedatabase.utils

import androidx.annotation.StringRes
import com.lmiceli.gamedatabase.R

object ErrorMapper {

    private const val UNAUTHORIZED = 401

    @StringRes
    fun getMessage(code: Int): Int =
        when (code) {
            UNAUTHORIZED -> R.string.unauthorized_network_error
            else -> R.string.generic_network_error
        }
}
