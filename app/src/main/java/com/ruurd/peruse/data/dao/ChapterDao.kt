package com.ruurd.peruse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ruurd.peruse.data.pojo.ChapterPOJO

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters")
    fun getChapters(): List<ChapterPOJO>

    @Query("SELECT * FROM chapters WHERE bookId is :bookId")
    fun getChapters(bookId: Long): List<ChapterPOJO>

    @Query("SELECT * FROM chapters WHERE chapterId is :id ORDER BY chapterId ASC LIMIT 1")
    fun get(id: Long) : ChapterPOJO

    @Insert
    suspend fun insert(chapterPOJO: ChapterPOJO): Long

    @Insert
    suspend fun insert(vararg chapterPOJO: ChapterPOJO): List<Long>

    @Insert
    suspend fun insertAll(chapters: List<ChapterPOJO>): List<Long>

    @Update
    suspend fun update(vararg chapterPOJO: ChapterPOJO)

    @Update
    suspend fun update(chapters: List<ChapterPOJO>)
}