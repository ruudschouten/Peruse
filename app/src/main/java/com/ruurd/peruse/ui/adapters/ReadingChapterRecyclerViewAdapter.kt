package com.ruurd.peruse.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruurd.peruse.databinding.RecyclerChaptersReadBinding
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.ui.adapters.abstracts.BaseRecyclerAdapter
import com.ruurd.peruse.ui.adapters.viewholders.ReadingChapterViewHolder

class ReadingChapterRecyclerViewAdapter(chapters: MutableList<Chapter>) :
    BaseRecyclerAdapter<Chapter, ReadingChapterViewHolder>(chapters) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingChapterViewHolder {
        return ReadingChapterViewHolder(
            RecyclerChaptersReadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReadingChapterViewHolder, position: Int) {
        holder.bind(getAt(position))
    }
}