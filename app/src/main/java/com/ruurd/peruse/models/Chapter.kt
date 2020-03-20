package com.ruurd.peruse.models

import android.content.Context
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.ChapterPOJO
import java.util.concurrent.TimeUnit

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
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration)

        return String.format(
            context.getString(
                R.string.time_format,
                minutes.toString(),
                seconds.toString()
            )
        )
    }

    override fun toPojo(): ChapterPOJO {
        return ChapterPOJO(title, pages, duration)
    }
}