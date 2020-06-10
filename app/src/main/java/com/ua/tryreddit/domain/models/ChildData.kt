package com.ua.tryreddit.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reddit_data",
    indices = [Index(value = ["subreddit"], unique = false)])
data class ChildData(
    @PrimaryKey
    @SerializedName("name")
    val name: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("subreddit")
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val subreddit: String,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("created")
    val created: Long,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("url")
    val url: String?
) {
    var responseIndex = -1
}