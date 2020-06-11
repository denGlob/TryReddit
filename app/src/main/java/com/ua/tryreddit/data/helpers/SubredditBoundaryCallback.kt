package com.ua.tryreddit.data.helpers

import androidx.annotation.MainThread
import com.ua.tryreddit.data.api.RedditApi
import com.ua.tryreddit.domain.helpers.AbstractRedditBoundaryCallback
import com.ua.tryreddit.domain.models.ChildData
import com.ua.tryreddit.domain.models.http.NewsResponse
import com.ua.tryreddit.utils.PagingRequestHelper
import com.ua.tryreddit.utils.trackStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class SubredditBoundaryCallback(
    private val request: String,
    private val requestApi: RedditApi,
    private val executor: Executor,
    private val pageSize: Int,
    private val responseHandler: (String, NewsResponse) -> Unit
): AbstractRedditBoundaryCallback() {

    val pagingHelper = PagingRequestHelper(executor)
    val state = pagingHelper.trackStatus()

    @MainThread
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        pagingHelper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val result = requestApi.getRedditTop(
                        subreddit = request,
                        limit = pageSize).await()
                    toDb(result, it)
                } catch (e: Exception) {
                    it.recordFailure(e)
                }
            }
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: ChildData) {
        super.onItemAtEndLoaded(itemAtEnd)
        pagingHelper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val result = requestApi.getRedditTopAfter(
                        subreddit = request,
                        after = itemAtEnd.name,
                        limit = pageSize).await()
                    toDb(result, it)
                } catch (e: Exception) {
                    it.recordFailure(e)
                }
            }
        }
    }

    private fun toDb(data: NewsResponse, pagingCallback: PagingRequestHelper.Request.Callback) {
        executor.execute {
            responseHandler(request, data)
            pagingCallback.recordSuccess()
        }
    }
}