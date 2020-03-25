package com.ruurd.peruse.ui.adapters.interfaces

import androidx.navigation.NavController

interface IPOJOViewHolder<T> {
    fun bind(pojo: T, navController: NavController)
}
