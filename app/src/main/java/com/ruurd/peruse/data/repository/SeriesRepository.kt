package com.ruurd.peruse.data.repository

import com.ruurd.peruse.data.dao.SeriesDao
import com.ruurd.peruse.data.pojo.SeriesPOJO

class SeriesRepository(private val dao: SeriesDao) {

    fun insert(pojo: SeriesPOJO) {
        dao.insert(pojo)
    }

    fun get(): List<SeriesPOJO> {
        return dao.getSeries()
    }

    fun get(name: String) : List<SeriesPOJO> {
        return dao.getByName(name)
    }

    fun insertOrGetByName(name: String): SeriesPOJO {
        // check if exists.
        if (dao.getByName(name).isNullOrEmpty()) {
            insert(SeriesPOJO(name))
        }
        return get(name).first()
    }
}