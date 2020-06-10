package com.ua.tryreddit.data.engines

import androidx.room.Database
import com.ua.tryreddit.domain.models.ChildData

@Database(entities = [ChildData::class], version = 1, exportSchema = false)
abstract class DbEngine {
    abstract fun provideDataDao(): RedditPostDao
}