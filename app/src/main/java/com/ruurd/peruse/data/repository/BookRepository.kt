package com.ruurd.peruse.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.ruurd.peruse.data.AppDatabase
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.FullBookPOJO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BookRepository(context: Context) : CoroutineScope {

    private val dao = AppDatabase.getInstance(context).bookDao()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    suspend fun insert(pojo: BookPOJO): Long {
        return dao.insert(pojo)
    }

    fun get(): LiveData<List<BookPOJO>> {
        return dao.getBooks()
    }

    fun getFull(): LiveData<List<FullBookPOJO>> {
        return dao.getFullBooks()
    }

    fun get(id: Long): LiveData<BookPOJO> {
        return dao.get(id)
    }

    fun getFull(id: Long): LiveData<FullBookPOJO> {
        return dao.getFull(id)
    }

    fun remove(book: FullBookPOJO) {
        launch {
            dao.delete(book.book)
        }
    }
}