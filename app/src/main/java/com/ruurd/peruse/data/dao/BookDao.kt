package com.ruurd.peruse.data.dao

import androidx.room.*
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.FullBookPOJO

@Dao
interface BookDao {

    // TODO: Look at Kotlin Flow instead of LiveData
    @Transaction
    @Query("SELECT * FROM books")
    fun getFullBooks(): List<FullBookPOJO>

    @Query("SELECT * FROM books WHERE bookId is :id ORDER BY bookId ASC LIMIT 1")
    fun getFull(id: Long): FullBookPOJO

    @Query("SELECT * FROM books")
    fun getBooks(): List<BookPOJO>

    @Query("SELECT * FROM books WHERE bookId is :id ORDER BY bookId ASC LIMIT 1")
    fun get(id: Long): BookPOJO

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bookPOJO: BookPOJO): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg bookPOJO: BookPOJO): List<Long>

    @Update
    fun update(vararg bookPOJO: BookPOJO)

    @Delete
    fun delete(vararg bookPOJO: BookPOJO)
}