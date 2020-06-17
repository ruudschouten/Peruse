package com.ruurd.peruse.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruurd.peruse.R
import com.ruurd.peruse.ui.adapters.LibraryRecyclerViewAdapter
import com.ruurd.peruse.ui.callbacks.LibrarySwipeCallback
import com.ruurd.peruse.ui.dialogs.AddBookDialogFragment
import com.ruurd.peruse.ui.activities.viewmodels.LibraryViewModel
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LibraryViewModel
    private lateinit var libraryAdapter: LibraryRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)
        libraryAdapter = LibraryRecyclerViewAdapter(listOf())
        activity_library_list.layoutManager = LinearLayoutManager(this)
        activity_library_list.adapter = libraryAdapter

        viewModel.getBooks.observe(this, Observer { books ->
            libraryAdapter.set(books)
            if (books.isNotEmpty()) {
                library_empty_data.visibility = View.GONE
            } else {
                library_empty_data.visibility = View.VISIBLE
            }
        })

        setSwipeListeners()

        activity_library_fab_add_book.setOnClickListener {
            AddBookDialogFragment().show(supportFragmentManager, "new_book")
        }
    }

    private fun setSwipeListeners() {
        val itemTouchHelper = ItemTouchHelper(LibrarySwipeCallback(libraryAdapter, viewModel))
        itemTouchHelper.attachToRecyclerView(activity_library_list)
    }
}
