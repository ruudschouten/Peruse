package com.ruurd.peruse.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.timer.State
import kotlinx.android.synthetic.main.dialog_reading_book.view.*
import kotlinx.android.synthetic.main.dialog_reading_book_finished.view.*
import kotlinx.android.synthetic.main.dialog_reading_book_timer.view.*

class BookReadingDialogFragment(var book: FullBookPOJO) : DialogFragment() {

    private lateinit var root: View

    private lateinit var pauseDrawable: Drawable
    private lateinit var playDrawable: Drawable

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        setupDrawables()

        val builder = AlertDialog.Builder(context!!)
        val inflater = requireActivity().layoutInflater
        root = inflater.inflate(R.layout.dialog_reading_book, null)

        setupFinishedViews()

        setupGeneralValues()
        setupTimerValues()
        setupFinishedValues()

        isCancelable = false
        builder.setView(root)
        return builder.create()
    }

    private fun setupDrawables() {
        pauseDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_pause_24dp)!!
        playDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_play_arrow_24dp)!!
    }

    private fun setupFinishedViews() {
//        doneReadingContainer = root.findViewById(R.id.dialog_reading_finished_container)
//        chaptersRead = root.findViewById(R.id.dialog_reading_chapter_amount)
//        chaptersRecycler = root.findViewById(R.id.dialog_reading_chapters)
//
//        //TODO: Chapters RecyclerAdapter
//
//        discardButton = root.findViewById(R.id.dialog_reading_discard_button)
//        addButton = root.findViewById(R.id.dialog_reading_add_button)
    }

    private fun setupGeneralValues() {
        root.dialog_reading_header.text = getString(R.string.dialog_reading_header, book.book.title)

        root.dialog_reading_start_button.setOnClickListener {
            root.dialog_reading_start_button_container.visibility = GONE
            root.dialog_reading_timer_buttons.visibility = VISIBLE
            root.dialog_reading_timer.start()
            setToggleDrawable()
        }

        root.dialog_reading_cancel_button.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun setupTimerValues() {
        root.dialog_reading_timer_toggle_play_button.setOnClickListener {
            if (root.dialog_reading_timer.getState() == State.RUNNING) {
                root.dialog_reading_timer.pause()
            } else {
                root.dialog_reading_timer.start()
            }
            setToggleDrawable()
        }

        root.dialog_reading_timer_stop_button.setOnClickListener {
            root.dialog_reading_timer.stop()
            root.dialog_reading_header.text = root.dialog_reading_timer.getTime()
            root.dialog_reading_timer_container.visibility = GONE
            root.dialog_reading_finished_container.visibility = VISIBLE
        }
    }

    private fun setupFinishedValues() {
        root.dialog_reading_discard_button.setOnClickListener {
            root.dialog_reading_timer.onDestroy()
            dialog?.cancel()
        }

        root.dialog_reading_add_button.setOnClickListener {
            root.dialog_reading_timer.onDestroy()
        }
    }

    private fun setToggleDrawable() {
        root.dialog_reading_timer_toggle_play_button.setImageDrawable(
            if (root.dialog_reading_timer.getState() == State.RUNNING) pauseDrawable
            else playDrawable
        )
    }
}