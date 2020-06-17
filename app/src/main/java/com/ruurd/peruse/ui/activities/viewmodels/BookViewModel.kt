package com.ruurd.peruse.ui.activities.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.data.repository.AppRepository
import com.ruurd.peruse.data.repository.BookRepository
import com.ruurd.peruse.data.repository.ChapterRepository
import com.ruurd.peruse.models.Book
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository = BookRepository(application)
    private val chapterRepository = ChapterRepository(application)
    private val appRepository = AppRepository(application)

    fun getBook(id: Long): LiveData<FullBookPOJO> {
        return bookRepository.getFull(id)
    }

    fun insert(chapter: ChapterPOJO) = viewModelScope.launch {
        chapterRepository.insert(chapter)
    }

    fun update(book: BookPOJO) = viewModelScope.launch {
        bookRepository.update(book)
    }

    fun update(book: Book) = viewModelScope.launch {
        appRepository.update(book)
    }
}