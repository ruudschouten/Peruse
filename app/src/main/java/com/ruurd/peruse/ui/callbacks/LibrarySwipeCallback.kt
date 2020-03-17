package com.ruurd.peruse.ui.callbacks

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
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

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            SwipeBackgroundHelper.paintDrawCommandToStart(
                c,
                viewHolder.itemView,
                R.drawable.ic_delete_24dp,
                dX
            )
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}