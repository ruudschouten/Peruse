package com.ruurd.peruse.data.repository

import android.content.Context
import com.ruurd.peruse.data.AppDatabase
import com.ruurd.peruse.models.Author
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.models.Series
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AppRepository(context: Context) : CoroutineScope {
    private val bookRepository = BookRepository(context)
    private val authorRepository = AuthorRepository(context)
    private val seriesRepository = SeriesRepository(context)
    private val chapterRepository = ChapterRepository(context)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    fun insert(bookTitle: String, authorName: String) = launch {
        val author = Author(authorName)
        insert(Book(bookTitle, mutableListOf(), author))
    }

    fun insert(
        bookTitle: String,
        authorName: String,
        seriesName: String,
        seriesEntry: Float
    ) = launch {
        val author = Author(authorName)
        val series = Series(seriesName)
        insert(Book(bookTitle, mutableListOf(), author, series, seriesEntry))
    }

    fun insert(book: Book): Long {
        // Create a POJO from book.
        val bookPojo = book.toPojo()
        var bookId = 0L

        launch {
            // Check if books author exists.
            val author = authorRepository.insertOrGetByName(book.author.name)
            bookPojo.bookAuthorId = author.authorId

            // Check if book is in a series.
            if (book.isInSeries()) {
                val series = seriesRepository.insertOrGetByName(book.series!!.name)
                bookPojo.bookSeriesId = series.seriesId
                bookPojo.seriesEntry = book.seriesEntry
            }

            bookId = bookRepository.insert(bookPojo)

            // Check if any chapters have been added.
            if (book.chapters.any()) {
                book.chapters.forEach {
                    val chapterPojo = it.toPojo()
                    chapterPojo.bookId = bookId
                    chapterRepository.insert(chapterPojo)
                }
            }
        }

        return bookId
    }
}