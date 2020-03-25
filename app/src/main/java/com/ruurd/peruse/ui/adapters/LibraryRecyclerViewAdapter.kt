package com.ruurd.peruse.ui.adapters

import android.view.LayoutInflater
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
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_library_book, parent, false)

        return LibraryViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getAt(position), navController)
    }
}