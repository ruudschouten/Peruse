package com.ruurd.peruse.models

import android.content.Context
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.util.TimeUtil

data class Book(
    var id: Long,
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
    ) : this(0L, title, chapters, author, null, 0f)

    constructor(
        title: String,
        chapters: MutableList<Chapter>,
        author: Author,
        series: Series?,
        seriesEntry: Float
    ) : this(0L, title, chapters, author, series, seriesEntry)

    fun totalTime(): Long {
        return chapters.map { it.duration }.sum()
    }

    fun averageChapterTime(): Long {
        if (chapters.size == 0) {
            return 0
        }
        return chapters.map { it.duration }.average().toLong()
    }

    fun averagePageTime(): Long {
        if (chapters.size == 0) {
            return 0
        }
        return chapters.map { it.duration / it.pages }.average().toLong()
    }

    fun formattedTotalTime(context: Context): String {
        return TimeUtil.toTime(totalTime()).format(context)
    }

    fun formattedAverageChapterTime(context: Context): String {
        return TimeUtil.toTime(averageChapterTime()).format(context)
    }

    fun formattedAveragePageTime(context: Context): String {
        return TimeUtil.toTime(averagePageTime()).format(context)
    }

    fun isInSeries(): Boolean {
        return series != null
    }

    fun toSimplePojo(): BookPOJO = BookPOJO(id, title, seriesEntry)

    override fun toPojo(): FullBookPOJO {
        val chapters = mutableListOf<ChapterPOJO>()
        this.chapters.forEach { chapters.add(it.toPojo(id)) }
        return FullBookPOJO(toSimplePojo(), chapters, author.toPojo(), series?.toPojo())
    }
}