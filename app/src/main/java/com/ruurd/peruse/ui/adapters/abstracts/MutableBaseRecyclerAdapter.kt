package com.ruurd.peruse.ui.adapters.abstracts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.ui.adapters.interfaces.IRecyclerAdapter

abstract class MutableBaseRecyclerAdapter<T, TViewHolder : RecyclerView.ViewHolder>(
    var entries: MutableList<T>
) : RecyclerView.Adapter<TViewHolder>(),
    IRecyclerAdapter<T> {

    override fun set(entries: List<T>) {
        this.entries = entries.toMutableList()
        notifyDataSetChanged()
    }

    override fun get(): List<T> = entries

    override fun getAt(position: Int): T = entries[position]

    override fun getItemCount(): Int = entries.size

    internal fun inflateView(parent: ViewGroup, @LayoutRes layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    fun add(entry: T) {
        entries.add(entry)
    }
}
