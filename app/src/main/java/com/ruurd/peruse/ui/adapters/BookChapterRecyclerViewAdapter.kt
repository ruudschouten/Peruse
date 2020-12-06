package com.ruurd.peruse.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.databinding.RecyclerBookChapterBinding
import com.ruurd.peruse.ui.adapters.abstracts.BaseRecyclerAdapter
import com.ruurd.peruse.ui.adapters.interfaces.OnChapterClicked
import com.ruurd.peruse.ui.adapters.viewholders.BookChapterViewHolder

class BookChapterRecyclerViewAdapter(
    chapters: MutableList<ChapterPOJO>,
    private val onChapterClicked: OnChapterClicked
) : BaseRecyclerAdapter<ChapterPOJO, BookChapterViewHolder>(chapters) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookChapterViewHolder {
        return BookChapterViewHolder(
            RecyclerBookChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookChapterViewHolder, position: Int) {
        holder.bind(getAt(position), onChapterClicked)
    }
}