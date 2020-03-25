package com.ruurd.peruse.models

import android.content.Context
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.util.TimeUtil

data class Chapter(
    val title: String,
    var pages: Int,
    var duration: Long
) : ModelToPojo<ChapterPOJO> {

    constructor(
        title: String,
        pages: Int
    ) : this(title, pages, 0L)

    fun getFormattedTime(context: Context): String {
        return TimeUtil.toTime(duration).format(context)
    }

    override fun toPojo(): ChapterPOJO {
        return ChapterPOJO(title, pages, duration)
    }
}