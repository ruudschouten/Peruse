package com.ruurd.peruse.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.repository.ChapterRepository
import com.ruurd.peruse.ui.dialogs.utils.DialogUtils
import kotlinx.android.synthetic.main.dialog_chapter_edit.view.*

class ChapterUpdateDialog(private var bookPojo: BookPOJO, private var chapterPojo: ChapterPOJO) :
    DialogFragment() {

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        val chapterRepo = ChapterRepository(requireContext())

        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        root = inflater.inflate(R.layout.dialog_chapter_edit, null)

        root.dialog_chapter_update_title.setText(chapterPojo.title)
        root.dialog_chapter_update_start.setText(chapterPojo.start.toString())
        root.dialog_chapter_update_end.setText(chapterPojo.end.toString())

        root.dialog_reading_discard_button.setOnClickListener {
            dialog?.cancel()
        }

        root.dialog_reading_update_button.setOnClickListener {
            if (isAnyFieldEmpty()) {
                return@setOnClickListener
            }

            val title = root.dialog_chapter_update_title.text.toString()
            val start = root.dialog_chapter_update_start.text.toString()
            val end = root.dialog_chapter_update_end.text.toString()

            val chapter = chapterPojo.toModel()
            chapter.title = title
            chapter.setStartAndEnd(start.toInt(), end.toInt())
            chapterRepo.insertOrUpdate(listOf(chapter.toPojo()), bookPojo.bookId)

            dialog?.cancel()
        }

        builder.setView(root)
        return builder.create()
    }

    private fun isAnyFieldEmpty(): Boolean {
        if (DialogUtils.isEmpty(
                root.dialog_chapter_update_title.text, root.dialog_chapter_update_start.text,
                root.dialog_chapter_update_end.text
            )
        ) {
            Snackbar.make(
                root, getString(R.string.dialog_empty_fields_notice_update, "chapter"),
                Snackbar.LENGTH_LONG
            ).show()

            return true
        }

        return false
    }
}