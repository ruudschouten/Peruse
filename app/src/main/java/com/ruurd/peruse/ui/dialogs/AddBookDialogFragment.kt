package com.ruurd.peruse.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import java.lang.IllegalStateException

class AddBookDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        val builder = AlertDialog.Builder(context!!)
        val inflater = requireActivity().layoutInflater
        val root = inflater.inflate(R.layout.dialog_new_book, null)

        // Getting views.

        val bookTitleTextEdit = root.findViewById<EditText>(R.id.dialog_book_title)
        val seriesSpinner = root.findViewById<Spinner>(R.id.dialog_book_series_spinner)
        val writerSpinner = root.findViewById<Spinner>(R.id.dialog_book_writer_spinner)
        val cancelButton = root.findViewById<Button>(R.id.dialog_book_btn_cancel)
        val addButton = root.findViewById<Button>(R.id.dialog_book_btn_add)

        // Set values for the spinners.

        // Setting events.
        cancelButton.setOnClickListener {
            dialog?.cancel()
        }

        addButton.setOnClickListener {
            Snackbar.make(root, String.format("Adding %s", bookTitleTextEdit.text), 500).show()
        }

        builder.setView(root)
        return builder.create()
    }

}