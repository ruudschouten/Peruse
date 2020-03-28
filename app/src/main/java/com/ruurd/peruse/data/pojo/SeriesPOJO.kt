package com.ruurd.peruse.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruurd.peruse.models.Series

@Entity(tableName = "series")
data class SeriesPOJO(
    val name: String
) : PojoToModel<Series> {

    constructor(id: Long, name: String) : this(name) {
        seriesId = id
    }

    @PrimaryKey(autoGenerate = true)
    var seriesId: Long = 0

    override fun toModel(): Series {
        return Series(seriesId, name)
    }
}