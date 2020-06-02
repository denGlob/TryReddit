package com.ua.tryreddit.domain.models.http

import com.google.gson.annotations.SerializedName

data class ChildResponse(
    @SerializedName("data")
    val childData: ChildDataResponse
)