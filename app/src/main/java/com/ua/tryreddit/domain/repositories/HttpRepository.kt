package com.ua.tryreddit.domain.repositories

import com.ua.tryreddit.domain.models.http.NewsResponse
import kotlinx.coroutines.Deferred

interface HttpRepository {

    suspend fun getRedditTop(after: String, limit: String) : Deferred<NewsResponse>
}