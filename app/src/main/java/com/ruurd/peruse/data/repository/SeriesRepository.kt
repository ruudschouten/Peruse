package com.ruurd.peruse.data.repository

import android.content.Context
import com.ruurd.peruse.data.AppDatabase
import com.ruurd.peruse.data.pojo.SeriesPOJO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SeriesRepository(context: Context) : CoroutineScope {

    private val dao = AppDatabase.getInstance(context).seriesDao()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    suspend fun insert(pojo: SeriesPOJO): Long {
        return dao.insert(pojo)
    }

    fun get(): List<SeriesPOJO> {
        return dao.getSeries()
    }

    fun get(name: String): List<SeriesPOJO> {
        return dao.getByName(name)
    }

    suspend fun insertOrGetByName(name: String): SeriesPOJO {
        // check if exists.
        if (dao.getByName(name).isNullOrEmpty()) {
            insert(SeriesPOJO(name))
        }
        return get(name).first()
    }

    fun update(series: SeriesPOJO) = launch {
        dao.update(series)
    }
}