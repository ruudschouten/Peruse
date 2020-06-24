package com.ruurd.peruse.ui.adapters

import android.view.ViewGroup
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.ui.adapters.abstracts.BaseRecyclerAdapter
import com.ruurd.peruse.ui.adapters.interfaces.OnChapterClicked

class BookChapterRecyclerViewAdapter(
    chapters: List<ChapterPOJO>
) : BaseRecyclerAdapter<ChapterPOJO, BookChapterViewHolder>(chapters) {

    private lateinit var onClickListener: OnChapterClicked

    fun setListener(clickListener: OnChapterClicked) {
        onClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookChapterViewHolder {
        return BookChapterViewHolder(inflateView(parent, R.layout.recycler_book_chapter))
    }

    override fun onBindViewHolder(holder: BookChapterViewHolder, position: Int) {
        holder.setListener(onClickListener)
        holder.bind(getAt(position))
    }
}