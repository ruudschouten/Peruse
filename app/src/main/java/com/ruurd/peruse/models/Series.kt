package com.ruurd.peruse.models

import com.ruurd.peruse.data.pojo.SeriesPOJO

data class Series(
    var name: String
) : ModelToPojo<SeriesPOJO> {
    private val books: MutableMap<Float, Book> = mutableMapOf()

    fun addBook(entry: Float, book: Book): Boolean {
        if (books.containsKey(entry)) {
            // Entry is already set for this series.
            return false
        }

        books[entry] = book
        return true
    }

    override fun toPojo(): SeriesPOJO {
        return SeriesPOJO(name)
    }
}