package com.ruurd.peruse.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.BookPOJO
import com.ruurd.peruse.data.pojo.ChapterPOJO
import com.ruurd.peruse.data.repository.ChapterRepository
import com.ruurd.peruse.databinding.DialogChapterEditBinding
import com.ruurd.peruse.ui.dialogs.utils.DialogUtils

class ChapterUpdateDialog(private var bookPojo: BookPOJO, private var chapterPojo: ChapterPOJO) :
    DialogFragment() {

    private lateinit var binding: DialogChapterEditBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        binding = DialogChapterEditBinding.inflate(LayoutInflater.from(requireContext()))

        val chapterRepo = ChapterRepository(requireContext())

        val builder = AlertDialog.Builder(requireContext())

        binding.dialogChapterUpdateTitle.setText(chapterPojo.title)
        binding.dialogChapterUpdateStart.setText(chapterPojo.start.toString())
        binding.dialogChapterUpdateEnd.setText(chapterPojo.end.toString())

        binding.dialogReadingDiscardButton.setOnClickListener {
            dialog?.cancel()
        }

        binding.dialogReadingUpdateButton.setOnClickListener {
            if (isAnyFieldEmpty()) {
                return@setOnClickListener
            }

            val title = binding.dialogChapterUpdateTitle.text.toString()
            val start = binding.dialogChapterUpdateStart.text.toString()
            val end = binding.dialogChapterUpdateEnd.text.toString()

            val chapter = chapterPojo.toModel()
            chapter.title = title
            chapter.setStartAndEnd(start.toInt(), end.toInt())
            chapterRepo.insertOrUpdate(listOf(chapter.toPojo()), bookPojo.bookId)

            dialog?.cancel()
        }

        builder.setView(binding.root)
        return builder.create()
    }

    private fun isAnyFieldEmpty(): Boolean {
        if (DialogUtils.isEmpty(
                binding.dialogChapterUpdateTitle.text,
                binding.dialogChapterUpdateStart.text,
                binding.dialogChapterUpdateEnd.text
            )
        ) {
            Snackbar.make(
                binding.root, getString(R.string.dialog_empty_fields_notice_update, "chapter"),
                Snackbar.LENGTH_LONG
            ).show()

            return true
        }

        return false
    }
}