package com.ruurd.peruse.data.dao

import androidx.room.*
import com.ruurd.peruse.data.pojo.AuthorPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.models.Author

/**
 * Dao object for [AuthorPOJO]s.
 */
@Dao
interface AuthorDao : PeruseDao<AuthorPOJO> {
    /**
     * Retrieve all authors from the database in the form of [AuthorPOJO].
     */
    @Query("SELECT * FROM authors")
    fun getAuthors(): List<AuthorPOJO>

    /**
     * Retrieve all authors from the database where the name matches the given [name].
     */
    @Query("SELECT * FROM authors WHERE name is :name")
    fun getByName(name: String): List<AuthorPOJO>

    /**
     * Retrieve a single author based on the given [id].
     */
    @Query("SELECT * FROM authors WHERE authorId is :id ORDER BY authorId ASC LIMIT 1")
    fun getById(id: Long): AuthorPOJO
}