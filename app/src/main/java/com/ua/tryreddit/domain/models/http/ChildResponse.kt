package com.ua.tryreddit.domain.models.http

import com.google.gson.annotations.SerializedName
import com.ua.tryreddit.domain.models.ChildData

data class ChildResponse(
    @SerializedName("data")
    val childData: ChildData
)