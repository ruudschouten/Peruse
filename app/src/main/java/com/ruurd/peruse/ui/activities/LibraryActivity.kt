package com.ruurd.peruse.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.databinding.ActivityLibraryBinding
import com.ruurd.peruse.ui.activities.viewmodels.LibraryViewModel
import com.ruurd.peruse.ui.adapters.LibraryRecyclerViewAdapter
import com.ruurd.peruse.ui.callbacks.LibrarySwipeCallback
import com.ruurd.peruse.ui.dialogs.AddBookDialogFragment

class LibraryActivity : AppCompatActivity() {

    private lateinit var viewModel: LibraryViewModel
    private lateinit var libraryAdapter: LibraryRecyclerViewAdapter

    private lateinit var binding: ActivityLibraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)
        libraryAdapter = LibraryRecyclerViewAdapter(mutableListOf())
        binding.activityLibraryRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.activityLibraryRecyclerview.adapter = libraryAdapter

        viewModel.getBooks.observe(this, { updateBooks(it) })

        setSwipeListeners()

        binding.activityLibraryAddButton.setOnClickListener {
            AddBookDialogFragment().show(supportFragmentManager, "new_book")
        }
    }

    private fun setSwipeListeners() {
        val itemTouchHelper = ItemTouchHelper(LibrarySwipeCallback(libraryAdapter, viewModel))
        itemTouchHelper.attachToRecyclerView(binding.activityLibraryRecyclerview)
    }

    private fun updateBooks(books: List<FullBookPOJO>) {
        libraryAdapter.set(books)
        if (books.isNotEmpty()) {
            binding.activityLibraryRecyclerEmpty.root.visibility = View.GONE
        } else {
            binding.activityLibraryRecyclerEmpty.root.visibility = View.VISIBLE
        }
    }
}
