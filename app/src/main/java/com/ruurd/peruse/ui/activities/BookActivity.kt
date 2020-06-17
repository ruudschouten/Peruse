package com.ruurd.peruse.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.ui.activities.viewmodels.BookViewModel
import com.ruurd.peruse.ui.adapters.BookChapterRecyclerViewAdapter
import com.ruurd.peruse.ui.dialogs.BookReadingDialogFragment
import com.ruurd.peruse.ui.dialogs.ChapterTimeDialogFragment
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel
    private lateinit var chapterAdapter: BookChapterRecyclerViewAdapter

    private lateinit var book: FullBookPOJO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        val id = intent.extras!!.getLong("book_id")

        setupRecyclerView()

        viewModel.getBook(id).observe(this, Observer { book ->
            this.book = book
            setViewValues()
            setChapters()
        })

        activity_book_calculate.setOnClickListener {
            ChapterTimeDialogFragment(this.book.toModel()).show(supportFragmentManager, "calculate_chapter_time")
        }

        activity_book_start_reading_fab.setOnClickListener {
            // Navigate to reading fragment.
            BookReadingDialogFragment(this.book).show(supportFragmentManager, "reading_book")
        }
    }

    private fun setChapters() {
        chapterAdapter.set(book.chapters)
        if (book.chapters.isNotEmpty()) {
            activity_book_empty_data.visibility = View.GONE
        } else {
            activity_book_empty_data.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        chapterAdapter = BookChapterRecyclerViewAdapter(listOf())
        activity_book_chapter_list.layoutManager = LinearLayoutManager(this)
        activity_book_chapter_list.adapter = chapterAdapter
    }

    private fun setViewValues() {
        val model = book.toModel()
        activity_book_title.text = model.title
        activity_book_author.text =
            getString(R.string.book_written_by_author, model.author.name)

        if (model.isInSeries()) {
            activity_book_series.text = getString(
                R.string.series_with_entry,
                model.series!!.name,
                model.seriesEntry.toString()
            )
        } else {
            activity_book_series.visibility = View.GONE
        }

        if (model.chapters.isEmpty()) {
            activity_book_calculate.visibility = View.GONE
        } else {
            activity_book_calculate.visibility = View.VISIBLE
        }
    }
}