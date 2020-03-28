package com.ruurd.peruse.data.repository

import android.content.Context
import com.ruurd.peruse.data.AppDatabase
import com.ruurd.peruse.data.pojo.ChapterPOJO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ChapterRepository(context: Context) : CoroutineScope {

    private val dao = AppDatabase.getInstance(context).chapterDao()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    suspend fun insert(pojo: ChapterPOJO): Long {
        return dao.insert(pojo)
    }

    suspend fun insert(pojos: List<ChapterPOJO>): List<Long> {
        return dao.insertAll(pojos)
    }

    fun get(): List<ChapterPOJO> {
        return dao.getChapters()
    }

    fun get(chapterId: Long) : ChapterPOJO? {
        return dao.get(chapterId)
    }

    fun getForBook(bookId: Long): List<ChapterPOJO> {
        return dao.getChapters(bookId)
    }

    fun update(chapter: ChapterPOJO) = launch {
        dao.update(chapter)
    }

    fun update(chapters: List<ChapterPOJO>) = launch {
        dao.update(chapters)
    }

    fun insertOrUpdate(chapters: List<ChapterPOJO>, bookId: Long) = launch {
        // TODO: Check this :)
        chapters.forEach {
            it.bookId = bookId
            if (it.chapterId == 0L) {
                insert(it)
            } else if (get(it.chapterId) == null) {
                insert(it)
            }
            else {
                update(it)
            }
        }
    }
}