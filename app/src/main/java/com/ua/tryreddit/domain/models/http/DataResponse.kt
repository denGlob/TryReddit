package com.ua.tryreddit.domain.models.http

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("children")
    val child: List<ChildResponse>,
    @SerializedName("after")
    val after: String?,
    @SerializedName("before")
    val before: String?
)