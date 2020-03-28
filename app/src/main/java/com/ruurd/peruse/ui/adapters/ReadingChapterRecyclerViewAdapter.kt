package com.ruurd.peruse.ui.adapters

import android.view.ViewGroup
import com.ruurd.peruse.R
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.ui.adapters.abstracts.MutableBaseRecyclerAdapter

class ReadingChapterRecyclerViewAdapter(chapters: MutableList<Chapter>) :
    MutableBaseRecyclerAdapter<Chapter, ReadingChapterViewHolder>(chapters) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingChapterViewHolder {
        return ReadingChapterViewHolder(inflateView(parent, R.layout.recycler_chapters_read))
    }

    override fun onBindViewHolder(holder: ReadingChapterViewHolder, position: Int) {
    }
}