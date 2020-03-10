package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorPOJO(
    @PrimaryKey(autoGenerate = true) val authorId: Int,
    val name: String
)