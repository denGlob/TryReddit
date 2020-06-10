package com.ua.tryreddit.domain.network

sealed class NetworkState() {
    class Success() : NetworkState()
    class InProcess(): NetworkState()
    class Failed(val errorMessage: String? = null) : NetworkState()

    companion object {
        val SUCCESS = Success()
        val IN_PROCESS = InProcess()
        fun error(errorMsg: String?) = Failed(errorMsg)
    }
}