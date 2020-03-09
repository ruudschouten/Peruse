package com.ruurd.peruse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ruurd.peruse.R
import com.ruurd.peruse.ui.adapters.LibraryRecyclerViewAdapter
import com.ruurd.peruse.ui.dialogs.AddBookDialogFragment
import com.ruurd.peruse.ui.fragments.viewmodels.LibraryViewModel

class LibraryFragment : Fragment() {

    private lateinit var viewModel: LibraryViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var libraryAdapter: LibraryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_library, container, false)
        val navController = findNavController()

        recyclerView = root.findViewById(R.id.library_list)
        libraryAdapter = LibraryRecyclerViewAdapter(mutableListOf(), navController)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = libraryAdapter

        viewModel.getBooks().observe(viewLifecycleOwner, Observer { books ->
            libraryAdapter.overwriteBooks(books)
            libraryAdapter.notifyDataSetChanged()
        })

        val addBookFab: FloatingActionButton = root.findViewById(R.id.fab_add_book)
        addBookFab.setOnClickListener {
            AddBookDialogFragment().show(parentFragmentManager, "new_book")
        }
        return root
    }
}
