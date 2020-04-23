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
        val chaptersRead = model.chapters.size
        if(chaptersRead > 0) {
            view.library_book_recycler_chapter_count.text = context.getString(R.string.book_library_chapters_read, chaptersRead)
            view.library_book_recycler_average_chapter_time.text = context.getString(R.string.book_library_average_time, model.averageChapterTime(context))
            view.library_book_recycler_average_page_time.text = context.getString(R.string.book_library_page_average_time, model.averagePageTime(context))
        } else {
            view.library_book_recycler_chapter_count.visibility = View.GONE
            view.library_book_recycler_average_chapter_time.visibility = View.GONE
            view.library_book_recycler_average_page_time.visibility = View.GONE
        }
        if (model.series != null) {
            view.library_book_recycler_series.text = context.getString(R.string.book_series_with_entry, model.series!!.name, model.seriesEntry)
        }
    }

    override fun setupOnClickListener() {
        view.setOnClickListener {
            navController.navigate(
                LibraryFragmentDirections.actionNavigationLibraryToBookFragment(pojo.book.bookId)
            )
        }
    }
}