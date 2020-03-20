package com.ruurd.peruse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ruurd.peruse.data.pojo.SeriesPOJO

@Dao
interface SeriesDao {
    @Query("SELECT * FROM series")
    fun getSeries(): List<SeriesPOJO>

    @Query("SELECT * FROM series WHERE name is :name")
    fun getByName(name: String): List<SeriesPOJO>

    @Query("SELECT * FROM series WHERE seriesId is :id ORDER BY seriesId ASC LIMIT 1")
    fun getById(id: Long): SeriesPOJO

    @Insert
    suspend fun insert(seriesPOJO: SeriesPOJO): Long

    @Insert
    suspend fun insert(vararg seriesPOJO: SeriesPOJO): List<Long>

    @Update
    suspend fun update(vararg seriesPOJO: SeriesPOJO)
}