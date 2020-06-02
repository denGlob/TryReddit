package com.ua.tryreddit.domain.models.http

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("data")
    val data: DataResponse) : BaseResponse()