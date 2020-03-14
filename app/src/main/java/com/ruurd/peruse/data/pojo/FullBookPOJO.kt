package com.ruurd.peruse.data.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.models.Chapter

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
) : PojoToModel<Book> {
    override fun toModel(): Book {
        val chapters = mutableListOf<Chapter>()
        this.chapters.forEach { chapters.add(it.toModel()) }
        return Book(
            book.title,
            chapters,
            author.toModel(),
            series.toModel(),
            book.seriesEntry
        )
    }
}