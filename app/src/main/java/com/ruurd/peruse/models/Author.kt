package com.ruurd.peruse.models

import com.ruurd.peruse.data.pojo.AuthorPOJO

data class Author(
    var id: Long,
    var name: String
) : ModelToPojo<AuthorPOJO> {

    constructor(name: String) : this(0L, name)

    private val books: MutableList<Book> = mutableListOf()
    private val series: MutableList<Series> = mutableListOf()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun addSeries(series: Series) {
        this.series.add(series)
    }

    override fun toPojo(): AuthorPOJO {
        return AuthorPOJO(id, name)
    }
}