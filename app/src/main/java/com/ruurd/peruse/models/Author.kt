package com.ruurd.peruse.models

import com.ruurd.peruse.data.pojo.AuthorPOJO

data class Author(
    var name: String
) : ModelToPojo<AuthorPOJO> {
    private val books: MutableList<Book> = mutableListOf()
    private val series: MutableList<Series> = mutableListOf()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun addSeries(series: Series) {
        this.series.add(series)
    }

    override fun toPojo(): AuthorPOJO {
        return AuthorPOJO(name)
    }
}