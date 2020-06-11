package com.ua.tryreddit.domain.repositories

import com.ua.tryreddit.domain.helpers.Listing
import com.ua.tryreddit.domain.models.ChildData

interface RedditRepository {
    fun postsData(subReddit: String, pageSize: Int): Listing<ChildData>
}