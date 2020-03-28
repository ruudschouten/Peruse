package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruurd.peruse.models.Author

@Entity(tableName = "authors")
data class AuthorPOJO(
    val name: String
) : PojoToModel<Author> {

    constructor(id: Long, name: String) : this(name) {
        authorId = id
    }

    @PrimaryKey(autoGenerate = true)
    var authorId: Long = 0

    override fun toModel(): Author {
        return Author(authorId, name)
    }
}