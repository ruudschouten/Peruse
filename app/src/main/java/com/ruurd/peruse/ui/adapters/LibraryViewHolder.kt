package com.ruurd.peruse.ui.adapters

import android.view.View
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.ui.adapters.abstracts.POJOViewHolder
import com.ruurd.peruse.ui.fragments.LibraryFragmentDirections
import kotlinx.android.synthetic.main.recycler_library_book.view.*

class LibraryViewHolder(view: View) :
    POJOViewHolder<FullBookPOJO, Book>(view) {

    override fun setupViews() {
        view.library_book_recycler_title.text = model.title
        view.library_book_recycler_average_time.text =
            String.format("Average Time %.2f", model.averageTime())
        view.library_book_recycler_chapter_count.text =
            String.format("Chapters %s", model.chapters.size)
        view.library_book_recycler_series.text = model.series?.name
    }

    override fun setupOnClickListener() {
        view.setOnClickListener {
            navController.navigate(
                LibraryFragmentDirections.actionNavigationLibraryToBookFragment(pojo.book.bookId)
            )
        }
    }
}