package com.ruurd.peruse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruurd.peruse.data.pojo.AuthorPOJO

@Dao
interface AuthorDao {
    @Query("SELECT * FROM authors")
    fun getAuthors(): List<AuthorPOJO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg authorPOJO: AuthorPOJO)
}