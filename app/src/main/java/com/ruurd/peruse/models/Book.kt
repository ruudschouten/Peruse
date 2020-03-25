package com.ruurd.peruse.models

import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.pojo.FullBookPOJO

data class Book(
    val title: String,
    val chapters: MutableList<Chapter>,
    val author: Author,
    val series: Series?,
    val seriesEntry: Float
) : ModelToPojo<FullBookPOJO> {
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

    fun toSimplePojo() : BookPOJO = BookPOJO(title, seriesEntry)

    override fun toPojo(): FullBookPOJO {
        val chapters = mutableListOf<ChapterPOJO>()
        this.chapters.forEach { chapters.add(it.toPojo()) }
        return FullBookPOJO(toSimplePojo(), chapters, author.toPojo(), series?.toPojo())
    }
}