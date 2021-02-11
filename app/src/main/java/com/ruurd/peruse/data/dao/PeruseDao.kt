package com.ruurd.peruse.data.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

/**
 * Interface which contain functions which all Dao object should implement.
 */
interface PeruseDao<TPOJO> {
    /**
     * Insert a new [pojo].
     *
     * @return The assigned id for the inserted entity.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pojo: TPOJO): Long

    /**
     * Insert variable amounts of [pojo]s.
     *
     * @return The assigned id for the inserted entities.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg pojo: TPOJO): List<Long>

    /**
     * Update the entry of the [pojo] in the database with new changes.
     */
    @Update
    suspend fun update(vararg pojo: TPOJO)

    /**
     * Remove the [pojo] entry from the database.
     */
    @Delete
    suspend fun delete(vararg pojo: TPOJO)

    /**
     * Needs to be executed to ensure that all pending transactions are applied.
     */
    @RawQuery
    suspend fun checkpoint(query: SupportSQLiteQuery? = (SimpleSQLiteQuery("pragma wal_checkpoint(full)"))): Int
}