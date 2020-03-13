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
import com.ruurd.peruse.data.repository.*
import com.ruurd.peruse.models.Author
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.models.Series
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var bookRepository: BookRepository
    private lateinit var chapterRepository: ChapterRepository
    private lateinit var authorRepository: AuthorRepository
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var appRepository: AppRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        bookRepository = BookRepository(db.bookDao())
        chapterRepository = ChapterRepository(db.chapterDao())
        authorRepository = AuthorRepository(db.authorDao())
        seriesRepository = SeriesRepository(db.seriesDao())
        appRepository = AppRepository(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun createNewBook() {
        val bookPOJO = BookPOJO("Mistborn")
        bookRepository.insert(bookPOJO)

        val books = bookRepository.get()

        assert(books.size == 1)
    }

    @Test
    fun createFulBook() {
        authorRepository.insert(AuthorPOJO("Neil Gaiman"))
        val authors = authorRepository.get()
        assert(authors.size == 1)

        seriesRepository.insert(SeriesPOJO("American Gods"))
        val series = seriesRepository.get()
        assert(series.size == 1)


        val book = BookPOJO("Anansi Boys", authors[0].authorId, series[0].seriesId, 2f)
        val id = bookRepository.insert(book)
        chapterRepository.insert(
            mutableListOf(
                ChapterPOJO("1", 21, 0, id),
                ChapterPOJO("2", 18, 0, id),
                ChapterPOJO("3", 29, 0, id)
            )
        )
        val chapters = chapterRepository.get()
        assert(chapters.size == 3)

        val fullBooks = bookRepository.getFull()

        assert(fullBooks[0].author == authors[0])
        assert(fullBooks[0].series == series[0])
        assert(fullBooks[0].chapters[2] == chapters[2])
    }

    @Test
    fun insertFullBook() {
        val author = Author("Philip Pullman")
        val series = Series("The Book of Dust")
        val id = appRepository.insert(
            Book(
                "The Secret Commonwealth",
                mutableListOf(
                    Chapter("1", 21),
                    Chapter("2", 18)
                ),
                author,
                series,
                2F
            )
        )
        val book = bookRepository.getFull(id)

        assert(book.author.name == author.name)
        assert(book.series.name == series.name)
        assert(book.chapters.size == 2)
    }
}