package com.ruurd.peruse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ruurd.peruse.data.pojo.SeriesPOJO

@Dao
interface SeriesDao {
    @Query("SELECT * FROM series")
    fun getSeries(): List<SeriesPOJO>

    @Insert
    fun insert(vararg seriesPOJO: SeriesPOJO)
}