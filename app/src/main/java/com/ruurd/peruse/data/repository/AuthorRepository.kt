package com.ruurd.peruse.data.repository

import android.content.Context
import com.ruurd.peruse.data.AppDatabase
import com.ruurd.peruse.data.dao.AuthorDao
import com.ruurd.peruse.data.pojo.AuthorPOJO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AuthorRepository(context: Context) : CoroutineScope {

    private val dao = AppDatabase.getInstance(context).authorDao()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    suspend fun insert(pojo: AuthorPOJO): Long {
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

    suspend fun insertOrGetByName(name: String): AuthorPOJO {
        // Check if exists.
        if (get(name).isNullOrEmpty()) {
            insert(AuthorPOJO(name))
        }
        return get(name).first()
    }

    fun update(pojo: AuthorPOJO) = launch {
        dao.update(pojo)
    }
}