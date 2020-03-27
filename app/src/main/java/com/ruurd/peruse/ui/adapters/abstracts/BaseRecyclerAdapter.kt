package com.ruurd.peruse.ui.adapters.abstracts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.ui.adapters.interfaces.IRecyclerAdapter

abstract class BaseRecyclerAdapter<T, TViewHolder : RecyclerView.ViewHolder>(
    var entries: List<T>
) : RecyclerView.Adapter<TViewHolder>(),
    IRecyclerAdapter<T> {

    override fun set(entries: List<T>) {
        this.entries = entries
        notifyDataSetChanged()
    }

    override fun get(): List<T> = this.entries

    override fun getAt(position: Int): T = entries[position]

    override fun getItemCount(): Int = entries.size

    internal fun inflateView(parent: ViewGroup, @LayoutRes layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }
}