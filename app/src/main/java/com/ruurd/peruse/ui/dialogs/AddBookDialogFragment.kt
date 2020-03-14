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
import com.ruurd.peruse.R
import com.ruurd.peruse.data.repository.AppRepository

class AddBookDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        val builder = AlertDialog.Builder(context!!)
        val inflater = requireActivity().layoutInflater
        val root = inflater.inflate(R.layout.dialog_new_book, null)

        val appRepo = AppRepository(context!!)

        // Getting views.
        val bookTitleTextEdit = root.findViewById<EditText>(R.id.dialog_book_title)
        val bookAuthorTextEdit = root.findViewById<EditText>(R.id.dialog_book_author)
        val bookSeriesCheckbox = root.findViewById<CheckBox>(R.id.dialog_book_series_checkbox)
        val bookSeriesContainer =
            root.findViewById<ConstraintLayout>(R.id.dialog_book_series_container)
        val bookSeriesTextEdit = root.findViewById<EditText>(R.id.dialog_book_series)
        val bookSeriesEntryTextEdit = root.findViewById<EditText>(R.id.dialog_book_series_entry)
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
            // TODO: Add null checks or disable button until all required fields have values
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
}