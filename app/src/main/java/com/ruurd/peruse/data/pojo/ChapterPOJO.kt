package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterPOJO(
    @PrimaryKey(autoGenerate = true) val chapterId: Int,
    val bookId: Int,
    val title: String,
    val pages: Int,
    val duration: Long
)