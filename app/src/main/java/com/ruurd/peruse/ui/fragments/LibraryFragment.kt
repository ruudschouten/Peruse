package com.ruurd.peruse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruurd.peruse.R
import com.ruurd.peruse.ui.adapters.LibraryRecyclerViewAdapter
import com.ruurd.peruse.ui.callbacks.LibrarySwipeCallback
import com.ruurd.peruse.ui.dialogs.AddBookDialogFragment
import com.ruurd.peruse.ui.fragments.viewmodels.LibraryViewModel
import kotlinx.android.synthetic.main.fragment_library.view.*

class LibraryFragment : Fragment() {

    private lateinit var viewModel: LibraryViewModel
    private lateinit var libraryAdapter: LibraryRecyclerViewAdapter
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_library, container, false)
        val navController = findNavController()

        libraryAdapter = LibraryRecyclerViewAdapter(mutableListOf(), navController)
        root.fragment_library_list.layoutManager = LinearLayoutManager(activity)
        root.fragment_library_list.adapter = libraryAdapter

        viewModel.getBooks.observe(viewLifecycleOwner, Observer { books ->
            libraryAdapter.set(books)
        })

        setSwipeListeners()

        root.fragment_library_fab_add_book.setOnClickListener {
            AddBookDialogFragment().show(parentFragmentManager, "new_book")
        }
        return root
    }

    private fun setSwipeListeners() {
        val itemTouchHelper = ItemTouchHelper(LibrarySwipeCallback(libraryAdapter, viewModel))
        itemTouchHelper.attachToRecyclerView(root.fragment_library_list)
    }
}
