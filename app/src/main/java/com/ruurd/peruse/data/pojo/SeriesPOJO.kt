package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class SeriesPOJO(
    @PrimaryKey(autoGenerate = true) val seriesId: Int,
    val name: String
)