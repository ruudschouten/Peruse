package com.ruurd.peruse.ui.adapters

import android.view.ViewGroup
import androidx.navigation.NavController
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.ui.adapters.abstracts.BaseRecyclerAdapter

class LibraryRecyclerViewAdapter(
    books: List<FullBookPOJO>,
    private val navController: NavController
) : BaseRecyclerAdapter<FullBookPOJO, LibraryViewHolder>(books) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder(inflateView(parent, R.layout.recycler_library_book), parent.context)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getAt(position), navController)
    }
}