package com.ruurd.peruse.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.databinding.RecyclerLibraryBookBinding
import com.ruurd.peruse.ui.adapters.abstracts.BaseRecyclerAdapter
import com.ruurd.peruse.ui.adapters.viewholders.LibraryViewHolder

class LibraryRecyclerViewAdapter(
    books: MutableList<FullBookPOJO>
) : BaseRecyclerAdapter<FullBookPOJO, LibraryViewHolder>(books) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder(
            RecyclerLibraryBookBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getAt(position))
    }
}