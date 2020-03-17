package com.ruurd.peruse.ui.fragments

import android.graphics.Canvas
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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
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

        viewModel.getBooks.observe(viewLifecycleOwner, Observer { books ->
            libraryAdapter.setBooks(books)
        })

        setSwipeListeners()

        val addBookFab: FloatingActionButton = root.findViewById(R.id.fab_add_book)
        addBookFab.setOnClickListener {
            AddBookDialogFragment().show(parentFragmentManager, "new_book")
        }
        return root
    }

    private fun setSwipeListeners() {

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val book = libraryAdapter.getItem(position)

                viewModel.remove(book)
                recyclerView.adapter!!.notifyItemRemoved(position)

                val snackbar = Snackbar.make(
                    recyclerView,
                    String.format("Removed %s", book.book.title),
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction("UNDO") {
                    viewModel.insert(book.toModel())
                }
                snackbar.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
