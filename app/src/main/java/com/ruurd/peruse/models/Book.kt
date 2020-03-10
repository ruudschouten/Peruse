package com.ruurd.peruse.models

data class Book(
    var title: String,
    var chapters: MutableList<Chapter>
) {
    fun addChapter(chapter: Chapter) {
        chapters.add(chapter)
    }

    fun averageTime(): Double {
        if (chapters.size == 0) {
            return 0.0
        }
        return chapters.map { it.duration }.average()
    }
}