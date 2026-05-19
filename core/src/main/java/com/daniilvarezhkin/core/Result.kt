package com.daniilvarezhkin.core

typealias RootError = Error

sealed interface Result<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(val data: D): Result<D, E>
    data class Error<out D, out E: RootError>(val error: E): Result<D, E>

    fun onSuccess(action: (D) -> Unit): Result<D, E> {
        if(this is Success) action(data)
        return this
    }

    fun onError(action: (E) -> Unit): Result<D, E> {
        if(this is Error) action(error)
        return this
    }

    fun getDataOrNull(): D? {
        return if(this is Success) data
        else null
    }

    fun getErrorOrNull(): E? {
        return if(this is Error) error
        else null
    }
}
