package com.ua.tryreddit.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ua.tryreddit.domain.repositories.RedditRepository

class MainViewModel(private val repo: RedditRepository) : ViewModel() {

    private val requestLiveData = MutableLiveData<String>()
    private val repoResult = Transformations.map(requestLiveData) {
        repo.postsData(it, 10)
    }

    val data = Transformations.switchMap(repoResult) { it.dataList }
    val retryState = Transformations.switchMap(repoResult) { it.retryState }
    val refreshState = Transformations.switchMap(repoResult) { it.refreshState }

    fun refresh() = repoResult.value?.refresh?.invoke()

    fun retry() = repoResult.value?.retry?.invoke()

    fun loadData(request: String) : Boolean {
        if (requestLiveData.value == request) {
            return false
        }
        requestLiveData.value = request
        return true
    }
}
