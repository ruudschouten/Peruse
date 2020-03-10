package com.ruurd.peruse

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ruurd.peruse.data.AppDatabase
import com.ruurd.peruse.data.dao.AuthorDao
import com.ruurd.peruse.data.dao.BookDao
import com.ruurd.peruse.data.dao.ChapterDao
import com.ruurd.peruse.data.dao.SeriesDao
import com.ruurd.peruse.data.pojo.AuthorPOJO
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.pojo.SeriesPOJO
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var bookDao: BookDao
    private lateinit var chapterDao: ChapterDao
    private lateinit var authorDao: AuthorDao
    private lateinit var seriesDao: SeriesDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        bookDao = db.bookDao()
        chapterDao = db.chapterDao()
        authorDao = db.authorDao()
        seriesDao = db.seriesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun createNewBook() {
        val bookPOJO = BookPOJO(0, 0, 0, "Mistborn")
        bookDao.insert(bookPOJO)

        val books = bookDao.getBooks()

        assert(books.size == 1)
    }

    @Test
    fun createFulBook() {
        authorDao.insert(AuthorPOJO(1, "Neil Gaiman"))
        val authors = authorDao.getAuthors()
        assert(authors.size == 1)

        seriesDao.insert(SeriesPOJO(1, "American Gods"))
        val series = seriesDao.getSeries()
        assert(series.size == 1)

        chapterDao.insertAll(mutableListOf(
            ChapterPOJO(1, 1, "1", 21, 0L),
            ChapterPOJO(2, 1, "2", 18, 0L),
            ChapterPOJO(3, 1, "3", 29, 0L)
        ))
        val chapters = chapterDao.getChapters()
        assert(chapters.size == 3)

        val book = BookPOJO(1, 1, 1, "Anansi Boys")
        bookDao.insert(book)
        val fullBooks = bookDao.getFullBooks()

        assert(fullBooks[0].author == authors[0])
        assert(fullBooks[0].series == series[0])
        assert(fullBooks[0].chapters[2] == chapters[2])
    }
}