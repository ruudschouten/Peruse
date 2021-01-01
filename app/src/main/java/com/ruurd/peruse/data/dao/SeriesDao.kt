package com.ruurd.peruse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ruurd.peruse.data.pojo.SeriesPOJO

@Dao
interface SeriesDao : PeruseDao<SeriesPOJO> {
    /**
     * Retrieve all series.
     */
    @Query("SELECT * FROM series")
    fun getSeries(): List<SeriesPOJO>

    /**
     * Retrieve all series from the database where the name matches the given [name].
     */
    @Query("SELECT * FROM series WHERE name is :name")
    fun getByName(name: String): List<SeriesPOJO>

    /**
     * Retrieve a single series based on the given [id].
     */
    @Query("SELECT * FROM series WHERE seriesId is :id ORDER BY seriesId ASC LIMIT 1")
    fun getById(id: Long): SeriesPOJO
}