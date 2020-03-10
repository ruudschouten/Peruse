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

    @Query("SELECT * FROM books")
    fun getBooks() : List<BookPOJO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg bookPOJO: BookPOJO)

    @Update
    fun update(vararg bookPOJO: BookPOJO)

    @Delete
    fun delete(vararg bookPOJO: BookPOJO)
}