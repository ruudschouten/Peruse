package com.ruurd.peruse.models

import com.ruurd.peruse.data.pojo.ChapterPOJO

data class Chapter(
    val title: String,
    var pages: Int,
    var duration: Long
) : ModelToPojo<ChapterPOJO> {

    constructor(
        title: String,
        pages: Int
    ) : this(title, pages, 0L)

    override fun toPojo(): ChapterPOJO {
        return ChapterPOJO(title, pages, duration)
    }
}