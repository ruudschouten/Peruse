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

    fun averageTime(context: Context): String {
        if (chapters.size == 0) {
            return TimeUtil.toTime(0).format(context)
        }
        val average = chapters.map { it.duration }.average().toLong()
        return TimeUtil.toTime(average).format(context)
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