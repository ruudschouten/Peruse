package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorPOJO(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var authorId: Long = 0
}