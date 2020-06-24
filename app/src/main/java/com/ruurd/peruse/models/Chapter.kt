package com.ruurd.peruse.models

import android.content.Context
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.util.TimeUtil

data class Chapter(
    var id: Long,
    var title: String,
    var start: Int,
    var end: Int,
    var pages: Int,
    var duration: Long,
    var date: Long
) : ModelToPojo<ChapterPOJO> {

    constructor(
        start: Int,
        end: Int
    ) : this(0L, "", start, end, 0, 0L, 0L) {
        setBookCount()
    }

    constructor(
        title: String,
        start: Int,
        end: Int
    ) : this(0L, title, start, end, 0, 0L, 0L) {
        setBookCount()
    }

    constructor(
        title: String,
        start: Int,
        end: Int,
        date: Long
    ) : this(0L, title, start, end, 0, 0L, date) {
        setBookCount()
    }

    constructor(
        title: String,
        start: Int,
        end: Int,
        duration: Long,
        date: Long
    ) : this(0L, title, start, end, 0, duration, date) {
        setBookCount()
    }

    constructor() : this("", 0, 0)

    private fun setBookCount() {
        pages = (end - start) + 2
    }

    fun setStartAndEnd(start: Int, end: Int) {
        this.start = start
        this.end = end
        setBookCount()
    }

    fun estimatedDuration(book: Book): Long {
        return pages * book.averagePageTime()
    }

    fun formattedEstimatedDuraction(book: Book, context: Context): String {
        return TimeUtil.toTime(estimatedDuration(book)).format(context)
    }

    fun getFormattedTime(context: Context): String {
        return TimeUtil.toTime(duration).format(context)
    }

    override fun toPojo(): ChapterPOJO {
        return ChapterPOJO(title, start, end, pages, duration, date).also { it.chapterId = id }
    }

    fun toPojo(bookId: Long): ChapterPOJO {
        return toPojo().also { it.bookId = bookId }
    }
}