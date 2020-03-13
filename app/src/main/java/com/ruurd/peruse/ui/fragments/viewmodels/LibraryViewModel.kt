package com.ruurd.peruse.ui.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ruurd.peruse.models.Author
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.models.Chapter

class LibraryViewModel : ViewModel() {

    private val books: MutableLiveData<ArrayList<Book>>
        get() {
            return loadBooks()
        }

    fun getBooks(): LiveData<ArrayList<Book>> {
        return books
    }

    private fun loadBooks(): MutableLiveData<ArrayList<Book>> {
        // Mocking :)
        val book = Book(
            "His Dark Materials",
            mutableListOf(
                Chapter("1", 21),
                Chapter("2", 18)
            ),
            Author("Philip Pullman")
        )
        return MutableLiveData(arrayListOf(book))
    }
}