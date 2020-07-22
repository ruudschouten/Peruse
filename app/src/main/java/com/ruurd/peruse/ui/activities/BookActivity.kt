package com.ruurd.peruse.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.ui.activities.viewmodels.BookViewModel
import com.ruurd.peruse.ui.adapters.BookChapterRecyclerViewAdapter
import com.ruurd.peruse.ui.adapters.interfaces.OnChapterClicked
import com.ruurd.peruse.ui.dialogs.BookReadingDialogFragment
import com.ruurd.peruse.ui.dialogs.ChapterTimeDialogFragment
import com.ruurd.peruse.ui.dialogs.ChapterUpdateDialog
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity(), OnChapterClicked {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var chapterAdapter: BookChapterRecyclerViewAdapter

    private lateinit var book: FullBookPOJO
    private var bookId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        bookId = intent.extras!!.getLong("book_id")
        updateBook()

        setupRecyclerView()

        activity_book_bottombar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_menu_calculate -> {
                    val model = this.book.toModel()

                    if (model.chapters.isNotEmpty()) {
                        ChapterTimeDialogFragment(this.book.toModel()).show(
                            supportFragmentManager,
                            "calculate_chapter_time"
                        )
                    } else {
                        Snackbar.make(
                            activity_book,
                            "Unable to calculate how long reading anything takes",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    true
                }
                R.id.bottom_menu_chart -> {
                    true
                }
                else -> false
            }
        }

        activity_book_start_reading_fab.setOnClickListener {
            // Navigate to reading fragment.
            BookReadingDialogFragment(this.book).show(supportFragmentManager, "reading_book")
        }
    }

    private fun updateBook() {
        bookViewModel.getBook(bookId).observe(this, Observer { book ->
            this.book = book
            setViewValues()
            setChapters()
        })
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
        chapterAdapter.setListener(this)
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
        activity_book_time.text = getString(R.string.book_time_read, model.formattedTotalTime(this))
    }

    override fun onChapterClicked(chapter: ChapterPOJO) {
        ChapterUpdateDialog(book.book, chapter).show(supportFragmentManager, "update_chapter")
    }
}