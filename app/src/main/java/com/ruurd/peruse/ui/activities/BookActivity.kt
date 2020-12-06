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
import com.ruurd.peruse.databinding.ActivityBookBinding
import com.ruurd.peruse.ui.activities.viewmodels.BookViewModel
import com.ruurd.peruse.ui.adapters.BookChapterRecyclerViewAdapter
import com.ruurd.peruse.ui.adapters.interfaces.OnChapterClicked
import com.ruurd.peruse.ui.dialogs.BookReadingDialogFragment
import com.ruurd.peruse.ui.dialogs.ChapterTimeDialogFragment
import com.ruurd.peruse.ui.dialogs.ChapterUpdateDialog

class BookActivity : AppCompatActivity(), OnChapterClicked {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var chapterAdapter: BookChapterRecyclerViewAdapter

    private lateinit var book: FullBookPOJO
    private var bookId: Long = 0L

    private lateinit var binding: ActivityBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        bookId = intent.extras!!.getLong("book_id")
        updateBook()

        setupRecyclerView()

        binding.activityBookBottombar.setOnMenuItemClickListener { menuItem ->
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
                            binding.root,
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

        binding.activityBookStartReadingFab.setOnClickListener {
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
            binding.activityBookEmptyData.root.visibility = View.GONE
        } else {
            binding.activityBookEmptyData.root.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        chapterAdapter = BookChapterRecyclerViewAdapter(mutableListOf(), this)
        binding.activityBookChapterList.layoutManager = LinearLayoutManager(this)
        binding.activityBookChapterList.adapter = chapterAdapter
    }

    private fun setViewValues() {
        val model = book.toModel()
        binding.activityBookTitle.text = model.title
        binding.activityBookAuthor.text =
            getString(R.string.book_written_by_author, model.author.name)

        if (model.isInSeries()) {
            binding.activityBookSeries.text = getString(
                R.string.series_with_entry,
                model.series!!.name,
                model.seriesEntry.toString()
            )
        } else {
            binding.activityBookSeries.visibility = View.GONE
        }
        binding.activityBookTime.text =
            getString(R.string.book_time_read, model.formattedTotalTime(this))
    }

    override fun onChapterClicked(chapter: ChapterPOJO) {
        ChapterUpdateDialog(book.book, chapter).show(supportFragmentManager, "update_chapter")
    }
}