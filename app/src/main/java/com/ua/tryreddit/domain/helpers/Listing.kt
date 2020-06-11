package com.ua.tryreddit.domain.helpers

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
    val retryState: LiveData<NetworkState>,
    val refreshState : LiveData<NetworkState>,
    val dataList: LiveData<PagedList<T>>,
    val retry: () -> Unit,
    val refresh: () -> Unit
)