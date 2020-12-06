package com.ruurd.peruse.ui.adapters.interfaces

interface IRecyclerAdapter<T> {
    /**
     * Set the current entries to the given [entries].
     */
    fun set(entries: List<T>)

    /**
     * Add the [entry] to the end of the Adapter.
     */
    fun add(entry: T)

    /**
     * Retrieve all entries in this Adapter.
     */
    fun get(): List<T>

    /**
     * Get a specific element of type [T] at the given [position].
     */
    fun getAt(position: Int): T
}