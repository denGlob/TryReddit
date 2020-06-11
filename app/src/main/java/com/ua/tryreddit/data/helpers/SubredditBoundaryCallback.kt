package com.ua.tryreddit.data.helpers

import com.ua.tryreddit.domain.helpers.AbstractRedditBoundaryCallback
import com.ua.tryreddit.domain.models.ChildData

class SubredditBoundaryCallback: AbstractRedditBoundaryCallback() {

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