package com.ruurd.peruse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ruurd.peruse.data.dao.AuthorDao
import com.ruurd.peruse.data.dao.BookDao
import com.ruurd.peruse.data.dao.ChapterDao
import com.ruurd.peruse.data.dao.SeriesDao
import com.ruurd.peruse.data.pojo.AuthorPOJO
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.pojo.SeriesPOJO

@Database(
    entities = [
        BookPOJO::class,
        ChapterPOJO::class,
        AuthorPOJO::class,
        SeriesPOJO::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun chapterDao(): ChapterDao
    abstract fun authorDao(): AuthorDao
    abstract fun seriesDao(): SeriesDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "peruse_db").build()
        }
    }
}