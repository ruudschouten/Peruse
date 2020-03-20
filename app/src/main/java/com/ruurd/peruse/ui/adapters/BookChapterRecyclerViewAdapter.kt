package com.ruurd.peruse.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.ChapterPOJO

class BookChapterRecyclerViewAdapter(
    private var chapters: List<ChapterPOJO>,
    private var navController: NavController
) : RecyclerView.Adapter<BookChapterViewHolder>() {

    fun setChapters(chapters: List<ChapterPOJO>) {
        this.chapters = chapters
        notifyDataSetChanged()
    }

    fun getItem(position: Int): ChapterPOJO {
        return chapters[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookChapterViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_book_chapter, parent, false)

        return BookChapterViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = chapters.size

    override fun onBindViewHolder(holder: BookChapterViewHolder, position: Int) {
        holder.bind(chapters[position], navController)
    }
}