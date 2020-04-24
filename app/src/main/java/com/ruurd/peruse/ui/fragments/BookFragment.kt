package com.ruurd.peruse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.ui.adapters.BookChapterRecyclerViewAdapter
import com.ruurd.peruse.ui.dialogs.BookReadingDialogFragment
import com.ruurd.peruse.ui.fragments.viewmodels.BookViewModel
import kotlinx.android.synthetic.main.fragment_book.view.*

class BookFragment : Fragment() {


    private lateinit var viewModel: BookViewModel
    private lateinit var chapterAdapter: BookChapterRecyclerViewAdapter

    private lateinit var root: View
    private lateinit var navController: NavController

    private lateinit var book: FullBookPOJO

    private val args: BookFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_book, container, false)
        navController = findNavController()

        setupRecyclerView()

        viewModel.getBook(args.bookId).observe(viewLifecycleOwner, Observer { book ->
            this.book = book
            setViewValues()
            setChapters()
        })

        root.fragment_book_start_reading_fab.setOnClickListener {
            // Navigate to reading fragment.
            BookReadingDialogFragment(this.book).show(parentFragmentManager, "reading_book")
        }

        return root
    }

    private fun setChapters() {
        chapterAdapter.set(book.chapters)
        if (book.chapters.isNotEmpty()) {
            root.fragment_book_empty_data.visibility = GONE
        } else {
            root.fragment_book_empty_data.visibility = VISIBLE
        }
    }

    private fun setupRecyclerView() {
        chapterAdapter = BookChapterRecyclerViewAdapter(listOf(), navController)
        root.fragment_book_chapter_list.layoutManager = LinearLayoutManager(activity)
        root.fragment_book_chapter_list.adapter = chapterAdapter
    }

    private fun setViewValues() {
        val model = book.toModel()
        root.fragment_book_title.text = model.title
        root.fragment_book_author.text = getString(R.string.book_written_by_author, model.author.name)

        if (model.isInSeries()) {
            root.fragment_book_series.text = getString(
                R.string.series_with_entry,
                model.series!!.name,
                model.seriesEntry.toString()
            )
        } else {
            root.fragment_book_series.visibility = GONE
        }
    }
}