package com.ruurd.peruse.ui.adapters.abstracts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.ui.adapters.interfaces.IRecyclerAdapter

abstract class BaseRecyclerAdapter<T, TViewHolder : RecyclerView.ViewHolder>(
    private var entries: MutableList<T>
) : RecyclerView.Adapter<TViewHolder>(),
    IRecyclerAdapter<T> {

    override fun set(entries: List<T>) {
        this.entries = entries.toMutableList()
        notifyDataSetChanged()
    }

    override fun add(entry: T) {
        this.entries.add(entry)
        notifyDataSetChanged()
    }

    fun setAt(position: Int, entry: T) {
        this.entries[position] = entry
    }

    fun clear() {
        this.entries.clear()
    }

    override fun get(): List<T> = this.entries

    override fun getAt(position: Int): T = this.entries[position]

    override fun getItemCount(): Int = this.entries.size

    internal fun inflateView(parent: ViewGroup, @LayoutRes layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }
}