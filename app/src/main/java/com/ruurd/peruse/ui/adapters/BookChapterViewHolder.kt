package com.ruurd.peruse.ui.adapters

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.ui.adapters.abstracts.POJOViewHolder
import com.ruurd.peruse.ui.adapters.interfaces.OnChapterClicked
import com.ruurd.peruse.util.DateUtil
import kotlinx.android.synthetic.main.recycler_book_chapter.view.*

class BookChapterViewHolder(v: View) : POJOViewHolder<ChapterPOJO, Chapter>(v) {

    private lateinit var onClickListener: OnChapterClicked

    fun setListener(clickListener: OnChapterClicked) {
        onClickListener = clickListener
    }

    override fun setupViews() {
        view.book_chapter_recycler_title.text = model.title
        view.book_chapter_recycler_date.text = DateUtil.format(model.date)
        view.book_chapter_recycler_pages.text = view.context.getString(R.string.book_chapters_pages, model.pages, model.start, model.end)
        view.book_chapter_recycler_time.text = model.getFormattedTime(view.context)
    }

    override fun setupOnClickListener() {
        view.setOnClickListener {
            Snackbar.make(view, String.format("Clicked %s!", model.title), 1000).show()
            onClickListener.onChapterClicked(model.toPojo())
        }
    }
}