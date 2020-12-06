package com.ruurd.peruse.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.repository.AppRepository
import com.ruurd.peruse.databinding.DialogNewBookBinding
import com.ruurd.peruse.ui.dialogs.utils.DialogUtils

class AddBookDialogFragment : DialogFragment() {

    private lateinit var binding: DialogNewBookBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        binding = DialogNewBookBinding.inflate(LayoutInflater.from(requireContext()))

        val appRepo = AppRepository(requireContext())

        val builder = AlertDialog.Builder(requireContext())

        // Setting events.
        binding.dialogBookSeriesCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.dialogBookSeriesContainer.visibility = View.VISIBLE
            } else {
                binding.dialogBookSeriesContainer.visibility = View.GONE
            }
        }

        binding.dialogReadingDiscardButton.setOnClickListener {
            dialog?.cancel()
        }

        binding.dialogReadingAddButton.setOnClickListener {
            if (isAnyFieldEmpty()) {
                return@setOnClickListener
            }

            val title = binding.dialogBookTitle.text.toString()
            val author = binding.dialogBookAuthor.text.toString()

            if (binding.dialogBookSeriesCheckbox.isChecked) {
                val series = binding.dialogBookSeries.text.toString()
                val entry = binding.dialogBookSeriesEntry.text.toString().toFloat()

                appRepo.insert(title, author, series, entry)
            } else {
                appRepo.insert(title, author)
            }
            dialog?.cancel()
        }

        builder.setView(binding.root)
        return builder.create()
    }

    private fun isAnyFieldEmpty(): Boolean {
        if (binding.dialogBookSeriesCheckbox.isChecked) {
            if (DialogUtils.isEmpty(
                    binding.dialogBookTitle.text,
                    binding.dialogBookAuthor.text,
                    binding.dialogBookSeries.text,
                    binding.dialogBookSeriesEntry.text
                )
            ) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.dialog_empty_fields_notice, "book"),
                    Snackbar.LENGTH_LONG
                ).show()
                return true
            }
        } else {
            if (DialogUtils.isEmpty(binding.dialogBookTitle.text, binding.dialogBookAuthor.text)) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.dialog_empty_fields_notice, "book"),
                    Snackbar.LENGTH_LONG
                ).show()
                return true
            }
        }
        return false
    }
}