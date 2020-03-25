package com.ruurd.peruse.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.ruurd.peruse.timer.State
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.timer.ui.TimerView

class BookReadingDialogFragment(var book: FullBookPOJO) : DialogFragment() {

    private lateinit var root: View

    // region Views
    private lateinit var header: TextView

    private lateinit var timerContainer: ConstraintLayout
    private lateinit var timer: TimerView
    private lateinit var timerButtonContainer: ConstraintLayout
    private lateinit var timerToggleButton: ImageButton
    private lateinit var timerStopButton: ImageButton
    private lateinit var startButtonsContainer: ConstraintLayout
    private lateinit var startReadingButton: Button
    private lateinit var cancelReadingButton: Button

    private lateinit var doneReadingContainer: ConstraintLayout
    private lateinit var chaptersRead: EditText
    private lateinit var chaptersRecycler: RecyclerView
    private lateinit var discardButton: Button
    private lateinit var addButton: Button

    // endregion

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

        setupGeneralViews()
        setupTimerViews()
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

    private fun setupGeneralViews() {
        header = root.findViewById(R.id.dialog_reading_header)
    }

    private fun setupTimerViews() {
        timerContainer = root.findViewById(R.id.dialog_reading_timer_container)
        timer = root.findViewById(R.id.dialog_reading_timer)

        timerButtonContainer = root.findViewById(R.id.dialog_reading_timer_buttons)
        timerToggleButton = root.findViewById(R.id.dialog_reading_timer_toggle_play_button)
        timerStopButton = root.findViewById(R.id.dialog_reading_timer_stop_button)

        startButtonsContainer = root.findViewById(R.id.dialog_reading_start_button_container)
        startReadingButton = root.findViewById(R.id.dialog_reading_start_button)
        cancelReadingButton = root.findViewById(R.id.dialog_reading_cancel_button)
    }

    private fun setupFinishedViews() {
        doneReadingContainer = root.findViewById(R.id.dialog_reading_finished_container)
        chaptersRead = root.findViewById(R.id.dialog_reading_chapter_amount)
        chaptersRecycler = root.findViewById(R.id.dialog_reading_chapters)

        //TODO: Chapters RecyclerAdapter

        discardButton = root.findViewById(R.id.dialog_reading_discard_button)
        addButton = root.findViewById(R.id.dialog_reading_add_button)
    }

    private fun setupGeneralValues() {
        header.text = getString(R.string.dialog_reading_header, book.book.title)

        startReadingButton.setOnClickListener {
            startButtonsContainer.visibility = GONE
            timerButtonContainer.visibility = VISIBLE
            timer.start()
            setToggleDrawable()
        }

        cancelReadingButton.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun setupTimerValues() {
        timerToggleButton.setOnClickListener {
            if (timer.getState() == State.RUNNING) {
                timer.pause()
            } else {
                timer.start()
            }
            setToggleDrawable()
        }

        timerStopButton.setOnClickListener {
            timer.stop()
            header.text = timer.getTime()
            timerContainer.visibility = GONE
            doneReadingContainer.visibility = VISIBLE
        }
    }

    private fun setupFinishedValues() {
        discardButton.setOnClickListener {
            timer.onDestroy()
            dialog?.cancel()
        }

        addButton.setOnClickListener {
            timer.onDestroy()
        }
    }

    private fun setToggleDrawable() {
        timerToggleButton.setImageDrawable(
            if (timer.getState() == State.RUNNING) pauseDrawable
            else playDrawable
        )
    }
}