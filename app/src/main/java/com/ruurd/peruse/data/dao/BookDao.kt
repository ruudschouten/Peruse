package com.ruurd.peruse.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ruurd.peruse.data.pojo.AuthorPOJO
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.models.Book

/**
 * Dao object for [BookPOJO]s.
 */
@Dao
interface BookDao : PeruseDao<BookPOJO> {
    /**
     * Retrieve all books with all chapters and author data from the database.
     */
    @Transaction
    @Query("SELECT * FROM books")
    fun getFullBooks(): LiveData<List<FullBookPOJO>>

    /**
     * Retrieve a single book based on the given [id].
     */
    @Transaction
    @Query("SELECT * FROM books WHERE bookId is :id ORDER BY bookId ASC LIMIT 1")
    fun getFull(id: Long): LiveData<FullBookPOJO>

    /**
     * Retrieve all "simple" book entries.
     */
    @Query("SELECT * FROM books")
    fun getBooks(): LiveData<List<BookPOJO>>

    /**
     * Retrieve one "simple" book entry based on the given [id].
     */
    @Query("SELECT * FROM books WHERE bookId is :id ORDER BY bookId ASC LIMIT 1")
    fun get(id: Long): LiveData<BookPOJO>
}