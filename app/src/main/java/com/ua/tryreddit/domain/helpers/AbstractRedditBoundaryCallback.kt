package com.ua.tryreddit.domain.helpers

import androidx.paging.PagedList
import com.ua.tryreddit.domain.models.ChildData

abstract class AbstractRedditBoundaryCallback : PagedList.BoundaryCallback<ChildData>() {
}