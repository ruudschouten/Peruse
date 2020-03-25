package com.ruurd.peruse.ui.adapters.abstracts

import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.ui.adapters.interfaces.IRecyclerAdapter

abstract class BaseRecyclerAdapter<T, TViewHolder : RecyclerView.ViewHolder>(
    protected var entries: List<T>
) :
    RecyclerView.Adapter<TViewHolder>(),
    IRecyclerAdapter<T> {

    override fun set(entries: List<T>) {
        this.entries = entries
    }

    override fun get(): List<T> = this.entries

    override fun getAt(position: Int): T = entries[position]

    override fun getItemCount(): Int = entries.size
}