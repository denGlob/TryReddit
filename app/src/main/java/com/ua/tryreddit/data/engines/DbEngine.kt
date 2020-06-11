package com.ua.tryreddit.data.engines

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ua.tryreddit.domain.models.ChildData

@Database(entities = [ChildData::class], version = 1, exportSchema = false)
abstract class DbEngine : RoomDatabase() {
    abstract fun provideDataDao(): RedditPostDao
}