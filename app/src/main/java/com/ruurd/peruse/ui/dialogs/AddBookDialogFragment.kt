package com.ruurd.peruse.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
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

class AddBookDialogFragment : DialogFragment() {

    private lateinit var root: View
    private lateinit var bookTitleTextEdit: EditText
    private lateinit var bookAuthorTextEdit: EditText
    private lateinit var bookSeriesTextEdit: EditText
    private lateinit var bookSeriesEntryTextEdit: EditText
    private lateinit var bookSeriesCheckbox: CheckBox

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        val builder = AlertDialog.Builder(context!!)
        val inflater = requireActivity().layoutInflater
        root = inflater.inflate(R.layout.dialog_new_book, null)

        val appRepo = AppRepository(context!!)

        // Getting views.
        bookTitleTextEdit = root.findViewById(R.id.dialog_book_title)
        bookAuthorTextEdit = root.findViewById(R.id.dialog_book_author)
        bookSeriesCheckbox = root.findViewById(R.id.dialog_book_series_checkbox)
        val bookSeriesContainer =
            root.findViewById<ConstraintLayout>(R.id.dialog_book_series_container)
        bookSeriesTextEdit = root.findViewById(R.id.dialog_book_series)
        bookSeriesEntryTextEdit = root.findViewById(R.id.dialog_book_series_entry)
        val cancelButton = root.findViewById<Button>(R.id.dialog_book_btn_cancel)
        val addButton = root.findViewById<Button>(R.id.dialog_book_btn_add)

        // Setting events.
        bookSeriesCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                bookSeriesContainer.visibility = View.VISIBLE
            } else {
                bookSeriesContainer.visibility = View.GONE
            }
        }

        cancelButton.setOnClickListener {
            dialog?.cancel()
        }

        addButton.setOnClickListener {

            if (isAnyFieldEmpty()) {
                return@setOnClickListener
            }

            val title = bookTitleTextEdit.text.toString()
            val author = bookAuthorTextEdit.text.toString()

            if (bookSeriesCheckbox.isChecked) {
                val series = bookSeriesTextEdit.text.toString()
                val entry = bookSeriesEntryTextEdit.text.toString().toFloat()

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
        if (bookSeriesCheckbox.isChecked) {
            if (DialogUtils.isEmpty(
                    bookTitleTextEdit.text,
                    bookAuthorTextEdit.text,
                    bookSeriesTextEdit.text,
                    bookSeriesEntryTextEdit.text
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
            if (DialogUtils.isEmpty(bookTitleTextEdit.text, bookAuthorTextEdit.text)
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