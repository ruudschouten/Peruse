package com.ruurd.peruse.ui.fragments.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.data.repository.AppRepository
import com.ruurd.peruse.data.repository.BookRepository
import com.ruurd.peruse.models.Book
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository = BookRepository(application)
    private val appRepository = AppRepository(application)
    private val books = bookRepository.getFull()

    val getBooks: LiveData<List<FullBookPOJO>>
        get() {
            return books
        }

    fun insert(book: Book) = viewModelScope.launch {
        appRepository.insert(book)
    }
}