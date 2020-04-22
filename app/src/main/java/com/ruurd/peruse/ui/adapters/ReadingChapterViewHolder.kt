package com.ruurd.peruse.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.models.Chapter
import kotlinx.android.synthetic.main.recycler_chapters_read.view.*

class ReadingChapterViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    fun isAnyFieldEmpty(): Boolean {
        if (view.dialog_book_pages_start.text.isNullOrEmpty()) {
            return true
        }
        if (view.dialog_book_pages_end.text.isNullOrEmpty()) {
            return true
        }
        if (view.chapter_reading_title.text.isNullOrEmpty()) {
            return true
        }

        return false
    }

    fun getChapter(): Chapter {
        val start = view.dialog_book_pages_start.text.toString().toInt()
        val end = view.dialog_book_pages_end.text.toString().toInt()

        // Add two pages since start and end were also read
        val pages = (end - start) + 2
        return Chapter(view.chapter_reading_title.text.toString(), pages)
    }
}