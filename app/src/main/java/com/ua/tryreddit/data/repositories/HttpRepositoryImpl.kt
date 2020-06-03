package com.ua.tryreddit.data.repositories

import com.ua.tryreddit.data.engines.NetEngine
import com.ua.tryreddit.domain.api.RedditApi
import com.ua.tryreddit.domain.models.http.NewsResponse
import com.ua.tryreddit.domain.repositories.HttpRepository
import kotlinx.coroutines.Deferred

class HttpRepositoryImpl : HttpRepository {

    private val client = NetEngine.instance.provideRetrofit().create(RedditApi::class.java)

    override suspend fun getRedditTop(after: String, limit: String): Deferred<NewsResponse> =
        client.getRedditTop(after, limit)
}