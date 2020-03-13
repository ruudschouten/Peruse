package com.ruurd.peruse.models

import com.ruurd.peruse.data.pojo.ChapterPOJO

data class Chapter(
    val title: String,
    var pages: Int
) : ModelToPojo<ChapterPOJO> {
    var duration: Long = 0L

    override fun toPojo(): ChapterPOJO {
        return ChapterPOJO(title, pages, duration)
    }
}