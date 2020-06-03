package com.ua.tryreddit.domain.api

import com.ua.tryreddit.domain.models.http.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("top.json")
    fun getRedditTop(@Query("after") after: String,
                     @Query("limit") limit: String): Deferred<NewsResponse>
}