package com.ruurd.peruse.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.View
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.ui.activities.BookActivity
import com.ruurd.peruse.ui.adapters.abstracts.POJOViewHolder
import kotlinx.android.synthetic.main.recycler_library_book.view.*

class LibraryViewHolder(view: View, var context: Context) :
    POJOViewHolder<FullBookPOJO, Book>(view) {

    override fun setupViews() {
        view.library_book_recycler_title.text = model.title
        val chaptersRead = model.chapters.size
        if(chaptersRead > 0) {
            view.library_book_recycler_chapter_count.text = context.getString(R.string.book_library_chapters_read, chaptersRead)
            view.library_book_recycler_average_chapter_time.text = context.getString(R.string.book_library_average_time, model.formattedAverageChapterTime(context))
            view.library_book_recycler_average_page_time.text = context.getString(R.string.book_library_page_average_time, model.formattedAveragePageTime(context))
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
            // Navigate to Book Activity
            val intent = Intent(context, BookActivity::class.java)
            intent.putExtra("book_id", pojo.book.bookId)
            context.startActivity(intent)
        }
    }
}