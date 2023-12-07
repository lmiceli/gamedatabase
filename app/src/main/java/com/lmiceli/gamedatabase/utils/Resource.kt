package com.lmiceli.gamedatabase.utils

sealed class Resource<out T> {
    abstract val data: T?
    abstract val message: String?
    abstract fun isLoading(): Boolean

    data class SUCCESS<out T>(
        override val data: T,
        override val message: String? = null
    ) : Resource<T>() {
        override fun isLoading(): Boolean = false
    }

    data class CACHED<out T>(
        override val data: T,
        override val message: String? = null
    ) : Resource<T>() {
        override fun isLoading(): Boolean = true
    }

    data class LOADING<out T>(
        override val data: T? = null,
        override val message: String? = null
    ) : Resource<T>() {
        override fun isLoading(): Boolean = true
    }

    data class ERROR<out T>(
        override val message: String? = null,
        override val data: T? = null,
        val errorCode: Int? = null
    ) : Resource<T>() {
        override fun isLoading(): Boolean = false
    }
}
