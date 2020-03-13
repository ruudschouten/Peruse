package com.ruurd.peruse.data.repository

import com.ruurd.peruse.data.AppDatabase
import com.ruurd.peruse.models.Book

class AppRepository(db: AppDatabase) {
    private val bookRepository = BookRepository(db.bookDao())
    private val authorRepository = AuthorRepository(db.authorDao())
    private val seriesRepository = SeriesRepository(db.seriesDao())
    private val chapterRepository = ChapterRepository(db.chapterDao())

    fun insert(book: Book) : Long {
        // Create a POJO from book.
        val bookPojo = book.toPojo()
        // Check if books author exists.
        val author = authorRepository.insertOrGetByName(book.author.name)
        bookPojo.bookAuthorId = author.authorId

        // Check if book is in a series.
        if (book.isInSeries()) {
            val series = seriesRepository.insertOrGetByName(book.series!!.name)
            bookPojo.bookSeriesId = series.seriesId
            bookPojo.seriesEntry = book.seriesEntry
        }

        val bookId = bookRepository.insert(bookPojo)

        // Check if any chapters have been added.
        if (book.chapters.any()) {
            book.chapters.forEach {
                val chapterPojo = it.toPojo()
                chapterPojo.bookId = bookId
                chapterRepository.insert(chapterPojo)
            }
        }

        return bookId
    }
}