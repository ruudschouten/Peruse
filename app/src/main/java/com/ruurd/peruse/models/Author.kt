package com.ruurd.peruse.models

data class Author(
    var name: String
) {
    private val books: MutableList<Book> = mutableListOf()
    private val series: MutableList<Series> = mutableListOf()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun addSeries(series: Series) {
        this.series.add(series)
    }
}