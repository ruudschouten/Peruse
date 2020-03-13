package com.ruurd.peruse.data.repository

import com.ruurd.peruse.data.dao.BookDao
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.FullBookPOJO

class BookRepository(private val dao: BookDao) {

    fun insert(pojo: BookPOJO): Long {
        return dao.insert(pojo)
    }

    fun get(): List<BookPOJO> {
        return dao.getBooks()
    }

    fun getFull(): List<FullBookPOJO> {
        return dao.getFullBooks()
    }

    fun get(id: Long): BookPOJO {
        return dao.get(id)
    }

    fun getFull(id: Long): FullBookPOJO {
        return dao.getFull(id)
    }
}