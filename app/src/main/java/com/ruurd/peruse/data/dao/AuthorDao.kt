package com.ruurd.peruse.data.dao

import androidx.room.*
import com.ruurd.peruse.data.pojo.AuthorPOJO

@Dao
interface AuthorDao {
    @Query("SELECT * FROM authors")
    fun getAuthors(): List<AuthorPOJO>

    @Query("SELECT * FROM authors WHERE name is :name")
    fun getByName(name: String): List<AuthorPOJO>

    @Query("SELECT * FROM authors WHERE authorId is :id ORDER BY authorId ASC LIMIT 1")
    fun getById(id: Long): AuthorPOJO

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(authorPOJO: AuthorPOJO): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg authorPOJO: AuthorPOJO): List<Long>

    @Update
    suspend fun update(vararg authorPOJO: AuthorPOJO)
}