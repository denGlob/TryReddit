package com.ua.tryreddit.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ua.tryreddit.domain.helpers.NetworkState

fun PagingRequestHelper.trackStatus(): LiveData<NetworkState> {
    val liveData = MutableLiveData<NetworkState>()
    addListener {
        when{
            it.hasRunning() -> liveData.postValue(NetworkState.IN_PROCESS)
            it.hasError() -> liveData.postValue(NetworkState.error(getErrorFromStatusReport(it)))
            else -> liveData.postValue(NetworkState.SUCCESS)
        }
    }
    return liveData
}

fun getErrorFromStatusReport(report: PagingRequestHelper.StatusReport) =
    PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.first()