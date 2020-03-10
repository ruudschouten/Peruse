package com.ruurd.peruse.data.pojo

import androidx.room.Embedded
import androidx.room.Relation

data class FullBookPOJO(
    @Embedded
    val book: BookPOJO,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "bookId"
    )
    val chapters: List<ChapterPOJO>,
    @Relation(
        parentColumn = "bookAuthorId",
        entityColumn = "authorId"
    )
    val author: AuthorPOJO,
    @Relation(
        parentColumn = "bookSeriesId",
        entityColumn = "seriesId"
    )
    val series: SeriesPOJO
)