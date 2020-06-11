package com.ua.tryreddit.data.api

import com.ua.tryreddit.domain.models.http.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {

    @GET("/r/{subreddit}/top.json")
    fun getRedditTop(@Path("subreddit") subreddit: String,
                     @Query("limit") limit: Int): Deferred<NewsResponse>


    @GET("/r/{subreddit}/top.json")
    fun getRedditTopBefore(@Path("subreddit") subreddit: String,
                          @Query("before") after: String,
                          @Query("limit") limit: Int): Deferred<NewsResponse>
    @GET("/r/{subreddit}/top.json")
    fun getRedditTopAfter(@Path("subreddit") subreddit: String,
                          @Query("after") after: String,
                          @Query("limit") limit: Int): Deferred<NewsResponse>
}