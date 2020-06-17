package com.ruurd.peruse.ui.adapters

import android.view.ViewGroup
import androidx.navigation.NavController
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.ui.adapters.abstracts.BaseRecyclerAdapter

class BookChapterRecyclerViewAdapter(
    chapters: List<ChapterPOJO>
) : BaseRecyclerAdapter<ChapterPOJO, BookChapterViewHolder>(chapters) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookChapterViewHolder {
        return BookChapterViewHolder(inflateView(parent, R.layout.recycler_book_chapter))
    }

    override fun onBindViewHolder(holder: BookChapterViewHolder, position: Int) {
        holder.bind(getAt(position))
    }
}