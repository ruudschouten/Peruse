package com.ruurd.peruse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.ui.adapters.BookChapterRecyclerViewAdapter
import com.ruurd.peruse.ui.dialogs.BookReadingDialogFragment
import com.ruurd.peruse.ui.fragments.viewmodels.BookViewModel

class BookFragment : Fragment() {

    private lateinit var titleView: TextView
    private lateinit var seriesView: TextView
    private lateinit var authorView: TextView

    private lateinit var viewModel: BookViewModel
    private lateinit var chapterRecyclerView: RecyclerView
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

        setupViewValues()
        setupRecyclerView()

        viewModel.getBook(args.bookId).observe(viewLifecycleOwner, Observer { book ->
            this.book = book
            setViewValues()
            chapterAdapter.setChapters(book.chapters)
        })

        val fab = root.findViewById<FloatingActionButton>(R.id.fragment_book_start_reading_fab)
        fab.setOnClickListener {
            // Navigate to reading fragment.
            BookReadingDialogFragment(this.book).show(parentFragmentManager, "reading_book")
        }

        return root
    }

    private fun setupViewValues() {
        titleView = root.findViewById(R.id.fragment_book_title)
        seriesView = root.findViewById(R.id.fragment_book_series)
        authorView = root.findViewById(R.id.fragment_book_author)
    }

    private fun setupRecyclerView() {
        chapterRecyclerView = root.findViewById(R.id.fragment_book_chapter_list)
        chapterAdapter = BookChapterRecyclerViewAdapter(mutableListOf(), navController)
        chapterRecyclerView.layoutManager = LinearLayoutManager(activity)
        chapterRecyclerView.adapter = chapterAdapter
    }

    private fun setViewValues() {
        val model = book.toModel()
        titleView.text = model.title
        authorView.text = getString(R.string.book_written_by_author, model.author.name)

        if (model.isInSeries()) {
            seriesView.text = getString(
                R.string.series_with_entry,
                model.series!!.name,
                model.seriesEntry.toString()
            )
        } else {
            seriesView.visibility = GONE
        }
    }
}