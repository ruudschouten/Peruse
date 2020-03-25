package com.ruurd.peruse.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.ui.adapters.abstracts.BaseRecyclerAdapter

class BookChapterRecyclerViewAdapter(
    chapters: List<ChapterPOJO>,
    private var navController: NavController
) : BaseRecyclerAdapter<ChapterPOJO, BookChapterViewHolder>(chapters) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookChapterViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_book_chapter, parent, false)

        return BookChapterViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: BookChapterViewHolder, position: Int) {
        holder.bind(getAt(position), navController)
    }
}