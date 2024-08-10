package com.sam.gogoidea.repo.data

sealed class State<out T> {
    data object Loading : State<Nothing>()
    data object Empty : State<Nothing>()
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception: Exception) : State<Nothing>()
}
