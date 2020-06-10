package com.ua.tryreddit.data.engines

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ua.tryreddit.domain.models.ChildData

@Dao
interface RedditPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<ChildData>)

    @Query("SELECT * FROM reddit_data WHERE subreddit = :subreddit ORDER BY responseIndex ASC")
    fun itemsBySubreddit(subreddit: String) : DataSource.Factory<Int, String>

    @Query("DELETE FROM reddit_data WHERE subreddit = :subreddit")
    fun deleteBySubreddit(subreddit: String)

    @Query("SELECT MAX(responseIndex) + 1 FROM reddit_data WHERE subreddit = :subreddit")
    fun getNext(subreddit: String) : Int
}