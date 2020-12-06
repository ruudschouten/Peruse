package com.ruurd.peruse.ui.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.databinding.RecyclerChaptersReadBinding
import com.ruurd.peruse.models.Chapter

class ReadingChapterViewHolder(private val binding: RecyclerChaptersReadBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(chapter: Chapter) {
        binding.chapterReadingTitle.setText(chapter.title)
        // Only set the value of the pages if it isn't 0. If the start is 0, it's a default Chapter.
        if (chapter.start != 0) {
            binding.dialogBookPagesStart.setText(chapter.start.toString())
        }
    }

    fun isAnyFieldEmpty(): Boolean {
        return when {
            binding.dialogBookPagesStart.text.isNullOrEmpty() -> true
            binding.dialogBookPagesEnd.text.isNullOrEmpty() -> true
            binding.chapterReadingTitle.text.isNullOrEmpty() -> true
            else -> false
        }
    }

    fun getChapter(): Chapter {
        val start = binding.dialogBookPagesStart.text.toString().toInt()
        val end = binding.dialogBookPagesEnd.text.toString().toInt()

        return Chapter(
            binding.chapterReadingTitle.text.toString(),
            start,
            end
        )
    }
}