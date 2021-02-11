package com.ruurd.peruse.data.backup

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.ruurd.peruse.data.AppDatabase
import com.ruurd.peruse.data.DataUtil
import com.ruurd.peruse.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.*
import java.net.URI
import java.nio.channels.FileChannel

object BackupManager {

    /**
     * Create a backup of the database and store it on the local storage.
     */
    suspend fun createBackup(context: Context) {
        // Close the database.
        val db = AppDatabase.getInstance(context)
        db.executeCheckpoint()
        db.close()

        val dbFile = File(db.openHelper.writableDatabase.path)
        val saveLocation = File(
            DataUtil.getDefaultSaveLocation(context).absolutePath +
                    "/peruse_${DateUtil.format(System.currentTimeMillis())}.db"
        )
        dbFile.copyTo(saveLocation, true)

        Toast.makeText(context, "Backup created!", Toast.LENGTH_SHORT).show()
    }

    fun pickDatabaseFile(activity: Activity) {
        val intentType = "application/octet-stream"
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = intentType
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(intentType))
        }

        activity.startActivityForResult(intent, DataUtil.restoreResultCode)
    }

    suspend fun restoreBackup(context: Context, data: Intent?) {
        val db = AppDatabase.getInstance(context)
        db.close()

        val content = data!!.data!!

        withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(content) as FileInputStream
            val outputStream = FileOutputStream(db.openHelper.writableDatabase.path)
            var inputChannel: FileChannel? = null
            var outputChannel: FileChannel? = null

            try {
                inputChannel = inputStream.channel
                outputChannel = outputStream.channel
                inputChannel.transferTo(0, inputChannel.size(), outputChannel)
            } finally {
                inputChannel?.close()
                outputChannel?.close()
            }
        }

        Toast.makeText(context, "Backup restored!", Toast.LENGTH_SHORT).show()
    }
}