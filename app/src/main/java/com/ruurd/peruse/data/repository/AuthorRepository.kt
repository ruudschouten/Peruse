package com.ruurd.peruse.data.repository

import com.ruurd.peruse.data.dao.AuthorDao
import com.ruurd.peruse.data.pojo.AuthorPOJO

class AuthorRepository(private val dao: AuthorDao) {

    fun insert(pojo: AuthorPOJO) : Long {
        return dao.insert(pojo)
    }

    fun get(): List<AuthorPOJO> {
        return dao.getAuthors()
    }

    fun get(id: Long): AuthorPOJO {
        return dao.getById(id)
    }

    fun get(name: String): List<AuthorPOJO> {
        return dao.getByName(name)
    }

    fun insertOrGetByName(name: String): AuthorPOJO {
        // Check if exists.
        if (dao.getByName(name).isNullOrEmpty()) {
            insert(AuthorPOJO(name))
        }
        return get(name).first()
    }
}