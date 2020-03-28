package com.ruurd.peruse.models

import android.content.Context
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.util.TimeUtil

data class Chapter(
    var id: Long,
    var title: String,
    var pages: Int,
    var duration: Long
) : ModelToPojo<ChapterPOJO> {

    constructor(
        title: String,
        pages: Int
    ) : this(0L, title, pages, 0L)

    constructor(
        title: String,
        pages: Int,
        duration: Long
    ) : this(0L, title, pages, duration)

    constructor() : this("", 0)

    fun getFormattedTime(context: Context): String {
        return TimeUtil.toTime(duration).format(context)
    }

    override fun toPojo(): ChapterPOJO {
        return ChapterPOJO(id, title, pages, duration)
    }

    fun toPojo(bookId: Long) : ChapterPOJO {
        return ChapterPOJO(id, title, pages, duration).also { it.bookId = bookId }
    }
}