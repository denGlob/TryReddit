package com.ua.tryreddit.data.repositories

import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.room.Room
import com.ua.tryreddit.data.api.RedditApi
import com.ua.tryreddit.data.engines.DbEngine
import com.ua.tryreddit.data.engines.NetEngine
import com.ua.tryreddit.data.engines.RedditPostDao
import com.ua.tryreddit.data.helpers.SubredditBoundaryCallback
import com.ua.tryreddit.domain.helpers.Listing
import com.ua.tryreddit.domain.helpers.NetworkState
import com.ua.tryreddit.domain.models.ChildData
import com.ua.tryreddit.domain.models.http.NewsResponse
import com.ua.tryreddit.domain.repositories.RedditRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class DataRepository(context: Context) : RedditRepository {

    private val api: RedditApi
    private val dao: RedditPostDao
    private val db: DbEngine

    val executor = Executors.newSingleThreadExecutor()

    init {
        api = NetEngine.instance.provideRetrofit().create(RedditApi::class.java)
        db = Room.databaseBuilder(context, DbEngine::class.java, "reddit_database").build()
        dao = db.provideDataDao()
    }

    override fun postsData(subReddit: String, pageSize: Int): Listing<ChildData> {
        val bCallback = SubredditBoundaryCallback(subReddit, api, executor, pageSize) { request, response -> cacheResultToDb(request, response) }
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh(subReddit)
        }

        val livePagedList = LivePagedListBuilder(dao.itemsBySubreddit(subReddit), pageSize)
            .setBoundaryCallback(bCallback)
            .build()

        return Listing(
            retry = {
                bCallback.pagingHelper.retryAllFailed()
            },
            refresh = {
                refreshTrigger.value = null
            },
            refreshState = refreshState,
            retryState = bCallback.state,
            dataList = livePagedList
        )
    }

    @MainThread
    private fun refresh(subredditName: String): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.IN_PROCESS
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = api.getRedditTop(subredditName, 10).await()
                db.runInTransaction {
                    dao.deleteBySubreddit(subredditName)
                    cacheResultToDb(subredditName, result)
                }
                networkState.postValue(NetworkState.SUCCESS)
            } catch (e: Exception) {
                networkState.postValue(NetworkState.error(e.message))
            }
        }
        return networkState
    }

    private fun cacheResultToDb(request: String, response: NewsResponse) {
        response.data.child.let { items ->
            db.runInTransaction {
                val start = dao.getNext(request)
                val objs = items.mapIndexed { index, childResponse ->
                    childResponse.childData.responseIndex = start + index
                    childResponse.childData
                }
                dao.insert(objs)
            }
        }
    }
}