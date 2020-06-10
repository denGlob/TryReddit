package com.ua.tryreddit.domain.repositories.boundary

import androidx.paging.PagedList
import com.ua.tryreddit.domain.models.ChildData

class RedditBoundaryCallback : PagedList.BoundaryCallback<ChildData>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ChildData) {
        super.onItemAtEndLoaded(itemAtEnd)
    }

    override fun onItemAtFrontLoaded(itemAtFront: ChildData) {
        super.onItemAtFrontLoaded(itemAtFront)
    }
}