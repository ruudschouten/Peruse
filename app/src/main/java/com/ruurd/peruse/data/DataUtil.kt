package com.ruurd.peruse.data

import android.content.Context
import java.io.File

object DataUtil {
    const val databaseName = "peruse_db"
    const val restoreResultCode = 401

    fun getDefaultSaveLocation(context: Context): File {
        return context.getExternalFilesDir("backups")!!
    }
}