package com.ruurd.peruse.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.repository.AppRepository
import com.ruurd.peruse.ui.dialogs.utils.DialogUtils
import kotlinx.android.synthetic.main.dialog_new_book.*
import kotlinx.android.synthetic.main.dialog_new_book.view.*

class AddBookDialogFragment : DialogFragment() {

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        val appRepo = AppRepository(requireContext())

        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        root = inflater.inflate(R.layout.dialog_new_book, null)

        // Setting events.
        root.dialog_book_series_checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                root.dialog_book_series_container.visibility = View.VISIBLE
            } else {
                root.dialog_book_series_container.visibility = View.GONE
            }
        }

        root.dialog_reading_discard_button.setOnClickListener {
            dialog?.cancel()
        }

        root.dialog_reading_add_button.setOnClickListener {

            if (isAnyFieldEmpty()) {
                return@setOnClickListener
            }

            val title = root.dialog_book_title.text.toString()
            val author = root.dialog_book_author.text.toString()

            if (root.dialog_book_series_checkbox.isChecked) {
                val series = root.dialog_book_series.text.toString()
                val entry = root.dialog_book_series_entry.text.toString().toFloat()

                appRepo.insert(title, author, series, entry)
            } else {
                appRepo.insert(title, author)
            }
            dialog?.cancel()
        }

        builder.setView(root)
        return builder.create()
    }

    private fun isAnyFieldEmpty(): Boolean {
        if (root.dialog_book_series_checkbox.isChecked) {
            if (DialogUtils.isEmpty(
                    root.dialog_book_title.text,
                    root.dialog_book_author.text,
                    root.dialog_book_series.text,
                    root.dialog_book_series_entry.text
                )
            ) {
                Snackbar.make(
                    root,
                    getString(R.string.dialog_empty_fields_notice, "book"),
                    Snackbar.LENGTH_LONG
                ).show()
                return true
            }
        } else {
            if (DialogUtils.isEmpty(root.dialog_book_title.text, root.dialog_book_author.text)
            ) {
                Snackbar.make(
                    root,
                    getString(R.string.dialog_empty_fields_notice, "book"),
                    Snackbar.LENGTH_LONG
                ).show()
                return true
            }
        }
        return false
    }
}