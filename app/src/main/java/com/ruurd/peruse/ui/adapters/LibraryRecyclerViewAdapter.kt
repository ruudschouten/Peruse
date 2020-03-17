package com.ruurd.peruse.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.models.Book

class LibraryRecyclerViewAdapter(
    private var books: List<FullBookPOJO>,
    private val navController: NavController
) : RecyclerView.Adapter<LibraryViewHolder>() {

    fun setBooks(books: List<FullBookPOJO>) {
        this.books = books
        notifyDataSetChanged()
    }

    fun getItem(position: Int): FullBookPOJO {
        return books[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_library_book, parent, false)

        return LibraryViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(books[position], navController)
    }
}