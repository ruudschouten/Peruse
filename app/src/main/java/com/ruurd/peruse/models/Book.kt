package com.ruurd.peruse.models

import com.ruurd.peruse.data.pojo.BookPOJO

data class Book(
    val title: String,
    val chapters: MutableList<Chapter>,
    val author: Author,
    val series: Series?,
    val seriesEntry: Float
) : ModelToPojo<BookPOJO> {
    constructor(
        title: String,
        chapters: MutableList<Chapter>,
        author: Author
    ) : this(title, chapters, author, null, 0f)
    fun addChapter(chapter: Chapter) {
        chapters.add(chapter)
    }

    fun averageTime(): Double {
        if (chapters.size == 0) {
            return 0.0
        }
        return chapters.map { it.duration }.average()
    }

    fun isInSeries(): Boolean {
        return series != null
    }

    override fun toPojo(): BookPOJO {
        return BookPOJO(title, seriesEntry)
    }
}