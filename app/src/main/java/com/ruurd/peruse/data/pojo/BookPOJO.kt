package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookPOJO(
    val title: String,
    var bookAuthorId: Long,
    var bookSeriesId: Long,
    var seriesEntry: Float
) {
    constructor(
        title: String
    ) : this(title, 0, 0, 0f)

    constructor(
        title: String,
        seriesEntry: Float
    ) : this(title, 0, 0, seriesEntry)

    constructor(
        title: String,
        bookAuthorId: Long
    ) : this(title, bookAuthorId, 0, 0f)

    @PrimaryKey(autoGenerate = true)
    var bookId: Long = 0
}