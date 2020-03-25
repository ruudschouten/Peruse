package com.ruurd.peruse.ui.adapters.interfaces

interface IRecyclerAdapter<T> {
    fun set(entries: List<T>)
    fun get(): List<T>
    fun getAt(position: Int): T
}