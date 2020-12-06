package com.ruurd.peruse.ui.adapters.viewholders

import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.databinding.RecyclerBookChapterBinding
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.ui.adapters.abstracts.POJOViewHolder
import com.ruurd.peruse.ui.adapters.interfaces.OnChapterClicked
import com.ruurd.peruse.util.DateUtil

class BookChapterViewHolder(private val binding: RecyclerBookChapterBinding) :
    POJOViewHolder<ChapterPOJO, Chapter>(binding.root) {

    private lateinit var onChapterClicked: OnChapterClicked

    fun bind(pojo: ChapterPOJO, onChapterClicked: OnChapterClicked) {
        super.bind(pojo)
        this.onChapterClicked = onChapterClicked
    }

    override fun setupViews() {
        binding.bookChapterRecyclerTitle.text = model.title
        binding.bookChapterRecyclerDate.text = DateUtil.formatWithTime(model.date)
        binding.bookChapterRecyclerPages.text = view.context.getString(
            R.string.book_chapters_pages,
            model.pages,
            model.start,
            model.end
        )
        binding.bookChapterRecyclerTime.text = model.getFormattedTime(view.context)
    }

    override fun setupOnClickListener() {
        view.setOnClickListener {
            Snackbar.make(view, String.format("Clicked %s!", model.title), 1000).show()
            onChapterClicked.onChapterClicked(model.toPojo())
        }
    }
}