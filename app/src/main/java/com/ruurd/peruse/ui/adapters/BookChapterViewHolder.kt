package com.ruurd.peruse.ui.adapters

import android.view.View
import android.view.View.GONE
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.models.Chapter
import kotlinx.android.synthetic.main.recycler_book_chapter.view.*

class BookChapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var view: View = v
    private lateinit var chapter: Chapter

    fun bind(pojo: ChapterPOJO, navController: NavController) {
        chapter = pojo.toModel()

        if (chapter.duration > 0) {
            view.book_chapter_recycler_status.text = "✔"
        } else {
            view.book_chapter_recycler_status.visibility = GONE
        }

        view.book_chapter_recycler_title.text = chapter.title
        view.book_chapter_recycler_pages.text = chapter.pages.toString()
        view.book_chapter_recycler_time.text = chapter.getFormattedTime(view.context)

        view.setOnClickListener {
            // TODO: Navigate to chapter activity/dialog
            Snackbar.make(view, String.format("Clicked %s!", chapter.title), 1000).show()
        }
    }
}