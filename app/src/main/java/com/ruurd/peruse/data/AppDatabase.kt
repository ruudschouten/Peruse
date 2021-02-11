package com.ruurd.peruse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ruurd.peruse.data.DataUtil.databaseName
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

    suspend fun executeCheckpoint() {
        bookDao().checkpoint()
        chapterDao().checkpoint()
        authorDao().checkpoint()
        seriesDao().checkpoint()
    }

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
        }
    }
}