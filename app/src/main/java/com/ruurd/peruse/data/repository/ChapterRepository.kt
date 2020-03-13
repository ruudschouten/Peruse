package com.ruurd.peruse.data.repository

import com.ruurd.peruse.data.dao.ChapterDao
import com.ruurd.peruse.data.pojo.ChapterPOJO

class ChapterRepository(private val dao: ChapterDao) {

    fun insert(pojo: ChapterPOJO) {
        dao.insert(pojo)
    }

    fun insert(pojos: List<ChapterPOJO>) {
        dao.insertAll(pojos)
    }

    fun get(): List<ChapterPOJO> {
        return dao.getChapters()
    }

    fun get(bookId: Long) : List<ChapterPOJO> {
        return dao.getChapters(bookId)
    }
}