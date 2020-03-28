package com.ruurd.peruse.ui.adapters

import android.content.Context
import android.view.View
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.ui.adapters.abstracts.POJOViewHolder
import com.ruurd.peruse.ui.fragments.LibraryFragmentDirections
import kotlinx.android.synthetic.main.recycler_library_book.view.*

class LibraryViewHolder(view: View, var context: Context) :
    POJOViewHolder<FullBookPOJO, Book>(view) {

    override fun setupViews() {
        view.library_book_recycler_title.text = model.title
        view.library_book_recycler_average_time.text =
            context.getString(R.string.book_library_chapters_average_time, model.averageTime(context))
        view.library_book_recycler_chapter_count.text =
            context.getString(R.string.book_library_chapters_read, model.chapters.size.toString())
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