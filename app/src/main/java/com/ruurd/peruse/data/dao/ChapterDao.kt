package com.ruurd.peruse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ruurd.peruse.data.pojo.ChapterPOJO

/**
 * Dao object for [ChapterPOJO]s.
 */
@Dao
interface ChapterDao : PeruseDao<ChapterPOJO> {
    /**
     * Get all chapters.
     */
    @Query("SELECT * FROM chapters")
    fun getChapters(): List<ChapterPOJO>

    /**
     * Get all chapters which belong to a book with the given [bookId].
     */
    @Query("SELECT * FROM chapters WHERE bookId is :bookId")
    fun getChapters(bookId: Long): List<ChapterPOJO>

    /**
     * Retrieve a single chapter based on the given [id].
     */
    @Query("SELECT * FROM chapters WHERE chapterId is :id ORDER BY chapterId ASC LIMIT 1")
    fun getById(id: Long): ChapterPOJO?

    /**
     * Insert all given [chapters].
     *
     * @return The assigned ids for the inserted entities.
     */
    @Insert
    suspend fun insertAll(chapters: List<ChapterPOJO>): List<Long>

    /**
     * Update the entries of the given [chapters] with new changes.
     */
    @Update
    suspend fun update(chapters: List<ChapterPOJO>)
}