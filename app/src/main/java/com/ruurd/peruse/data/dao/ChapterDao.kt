package com.ruurd.peruse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ruurd.peruse.data.pojo.ChapterPOJO

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters")
    fun getChapters(): List<ChapterPOJO>

    @Query("SELECT * FROM chapters WHERE bookId = :bookId")
    fun getChapters(bookId: Int): List<ChapterPOJO>

    @Insert
    fun insert(vararg chapterPOJO: ChapterPOJO)
    
    @Insert
    fun insertAll(chapters: List<ChapterPOJO>)
}