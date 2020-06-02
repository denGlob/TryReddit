package com.ua.tryreddit.domain.models.http

import com.google.gson.annotations.SerializedName

data class ChildDataResponse(
    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("created")
    val created: Long,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("url")
    val url: String?
)