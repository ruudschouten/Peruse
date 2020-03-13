package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class SeriesPOJO(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var seriesId: Long = 0
}