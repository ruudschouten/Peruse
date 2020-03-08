package com.ruurd.peruse.ui.adapters

import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.models.Book
import kotlinx.android.synthetic.main.recycler_library_book.view.*

class LibraryViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var view: View = v
    private var book: Book? = null

    fun bind(book: Book, navController: NavController) {
        this.book = book

        view.library_book_title.text = book.title
        view.library_book_average_time.text = String.format("Chapters read %s", book.averageTime())
        view.library_book_chapter_count.text = String.format("Chapters read %s", book.chapters.size)

        view.setOnClickListener {
            // TODO: Navigate to book activity/dialog
        }
    }
}