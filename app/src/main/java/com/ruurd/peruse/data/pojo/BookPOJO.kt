package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookPOJO(
    @PrimaryKey(autoGenerate = true) val bookId: Int,
    val bookAuthorId: Int,
    val bookSeriesId: Int,
    val title: String
)