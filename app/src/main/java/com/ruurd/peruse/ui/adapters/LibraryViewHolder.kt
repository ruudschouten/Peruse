package com.ruurd.peruse.ui.adapters

import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.ui.fragments.LibraryFragmentDirections
import kotlinx.android.synthetic.main.recycler_library_book.view.*

class LibraryViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var view: View = v
    private lateinit var book: Book

    fun bind(pojo: FullBookPOJO, navController: NavController) {
        book = pojo.toModel()

        view.library_book_recycler_title.text = book.title
        view.library_book_recycler_average_time.text = String.format("Average Time %.2f", book.averageTime())
        view.library_book_recycler_chapter_count.text = String.format("Chapters %s", book.chapters.size)
        view.library_book_recycler_series.text = book.series?.name

        view.setOnClickListener {
            navController.navigate(
                LibraryFragmentDirections.actionNavigationLibraryToBookFragment(pojo.book.bookId)
            )
        }
    }
}