package com.ruurd.peruse.ui.adapters

import android.view.View
import android.view.View.GONE
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.ui.adapters.abstracts.POJOViewHolder
import kotlinx.android.synthetic.main.recycler_book_chapter.view.*

class BookChapterViewHolder(v: View)
    : POJOViewHolder<ChapterPOJO, Chapter>(v) {

    override fun setupViews() {
        if (model.duration > 0) {
            view.book_chapter_recycler_status.text = "✔"
        } else {
            view.book_chapter_recycler_status.visibility = GONE
        }

        view.book_chapter_recycler_title.text = model.title
        view.book_chapter_recycler_pages.text = model.pages.toString()
        view.book_chapter_recycler_time.text = model.getFormattedTime(view.context)
    }

    override fun setupOnClickListener() {
        view.setOnClickListener {
            // TODO: Navigate to chapter activity/dialog
            Snackbar.make(view, String.format("Clicked %s!", model.title), 1000).show()
        }
    }
}