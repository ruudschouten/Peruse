package com.ruurd.peruse.ui.callbacks

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.ui.adapters.LibraryRecyclerViewAdapter
import com.ruurd.peruse.ui.fragments.viewmodels.LibraryViewModel

class LibrarySwipeCallback
    (
    private val adapter: LibraryRecyclerViewAdapter,
    private val viewModel: LibraryViewModel
) : SwipeCallback() {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val book = adapter.getItem(position)

        viewModel.remove(book)
        adapter.notifyItemRemoved(position)

        val snackbar = Snackbar.make(
            viewHolder.itemView.rootView,
            String.format("Removed %s", book.book.title),
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("UNDO") {
            viewModel.insert(book.toModel())
        }
        snackbar.show()
    }
}