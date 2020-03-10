package com.ruurd.peruse.models

data class Chapter(
    val title: String,
    var pages: Int
) {
    var duration: Long = 0L
}