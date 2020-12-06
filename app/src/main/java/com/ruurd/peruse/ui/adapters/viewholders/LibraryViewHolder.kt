package com.ruurd.peruse.ui.adapters.viewholders

import android.content.Context
import android.content.Intent
import android.view.View
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.databinding.RecyclerLibraryBookBinding
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.ui.activities.BookActivity
import com.ruurd.peruse.ui.adapters.abstracts.POJOViewHolder

class LibraryViewHolder(
    private val binding: RecyclerLibraryBookBinding,
    private val context: Context
) :
    POJOViewHolder<FullBookPOJO, Book>(binding.root) {

    override fun setupViews() {
        binding.libraryBookRecyclerTitle.text = model.title
        val chaptersRead = model.chapters.size
        if (chaptersRead > 0) {
            binding.libraryBookRecyclerChapterCount.text =
                context.getString(R.string.book_library_chapters_read, chaptersRead)
            binding.libraryBookRecyclerAverageChapterTime.text = context.getString(
                R.string.book_library_average_time,
                model.formattedAverageChapterTime(context)
            )
            binding.libraryBookRecyclerAveragePageTime.text = context.getString(
                R.string.book_library_page_average_time,
                model.formattedAveragePageTime(context)
            )
        } else {
            binding.libraryBookRecyclerChapterCount.visibility = View.GONE
            binding.libraryBookRecyclerAverageChapterTime.visibility = View.GONE
            binding.libraryBookRecyclerAveragePageTime.visibility = View.GONE
        }
        if (model.series != null) {
            binding.libraryBookRecyclerSeries.text = context.getString(
                R.string.book_series_with_entry,
                model.series!!.name,
                model.seriesEntry
            )
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