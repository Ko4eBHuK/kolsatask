package com.okladnikov.kolsatask.utils

data class NetworkCallState<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun loading(message: String) = NetworkCallState(Status.LOADING, null, message)
        fun error(message: String) = NetworkCallState(Status.ERROR, null, message)
        fun <T> success(data: T) = NetworkCallState(Status.SUCCESS, data)
    }
}
