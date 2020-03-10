package com.ruurd.peruse.models

data class Series(
    var name: String
) {
    private val books: MutableMap<Float, Book> = mutableMapOf()

    fun addBook(entry: Float, book: Book): Boolean {
        if (books.containsKey(entry)) {
            // Entry is already set for this series.
            return false
        }

        books[entry] = book
        return true
    }
}