package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruurd.peruse.models.Chapter

@Entity(tableName = "chapters")
data class ChapterPOJO(
    val title: String,
    var pages: Int,
    var duration: Long,
    var bookId: Long
) : PojoToModel<Chapter> {
    constructor(
        title: String,
        pages: Int
    ) : this(title, pages, 0, 0)

    constructor(
        title: String,
        pages: Int,
        duration: Long
    ) : this(title, pages, duration, 0)

    constructor(
        id: Long,
        title: String,
        pages: Int,
        duration: Long
    ) : this(title, pages, duration, 0) {
        chapterId = id
    }

    @PrimaryKey(autoGenerate = true)
    var chapterId: Long = 0

    override fun toModel(): Chapter {
        return Chapter(chapterId, title, pages, duration)
    }
}