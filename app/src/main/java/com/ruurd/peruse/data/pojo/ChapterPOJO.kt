package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruurd.peruse.models.Chapter

@Entity(tableName = "chapters")
data class ChapterPOJO(
    val title: String,
    var start: Int,
    var end: Int,
    var pages: Int,
    var duration: Long,
    var date: Long,
    var bookId: Long
) : PojoToModel<Chapter> {
    constructor(
        title: String,
        start: Int,
        end: Int
    ) : this(title, start, end, 0, 0L, 0L, 0) {
        pages = (end - start) + 2
    }

    constructor(
        title: String,
        start: Int,
        end: Int,
        pages: Int,
        duration: Long,
        date: Long
    ) : this(title, start, end, pages, duration, date, 0)

    @PrimaryKey(autoGenerate = true)
    var chapterId: Long = 0

    override fun toModel(): Chapter {
        return Chapter(chapterId, title, start, end, pages, duration, date)
    }
}