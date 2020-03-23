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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.androchef.happytimer.countdowntimer.CircularCountDownView
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO

class BookReadingDialogFragment(var book: FullBookPOJO) : DialogFragment() {

    private lateinit var root: View

    private lateinit var timer: CircularCountDownView
    private lateinit var timerToggleButton: ImageButton
    private lateinit var timerStopButton: ImageButton

    private var playing = false
    private lateinit var pauseDrawable: Drawable
    private lateinit var playDrawable: Drawable

     private fun setupDrawables() {
        pauseDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_pause_24dp)!!
        playDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_play_arrow_24dp)!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        setupDrawables()

        val builder = AlertDialog.Builder(context!!)
        val inflater = requireActivity().layoutInflater
        root = inflater.inflate(R.layout.dialog_reading_book, null)

        // General views
        val header = root.findViewById<TextView>(R.id.dialog_reading_header)

        // Timer views
        timer = root.findViewById(R.id.dialog_reading_timer)

        val timerButtonContainer =
            root.findViewById<ConstraintLayout>(R.id.dialog_reading_timer_buttons)
        timerToggleButton = root.findViewById(R.id.dialog_reading_timer_toggle_play_button)
        timerStopButton = root.findViewById(R.id.dialog_reading_timer_stop_button)

        val startButtonsContainer =
            root.findViewById<ConstraintLayout>(R.id.dialog_reading_start_button_container)
        val startReadingButton = root.findViewById<Button>(R.id.dialog_reading_start_button)
        var cancelReadingButton = root.findViewById<Button>(R.id.dialog_reading_cancel_button)

        // Done reading views
        var chaptersRead = root.findViewById<EditText>(R.id.dialog_reading_chapter_amount)
        var chaptersRecycler = root.findViewById<RecyclerView>(R.id.dialog_reading_chapters)
        //TODO: Chapters RecyclerAdapter
        var discardButton = root.findViewById<Button>(R.id.dialog_reading_discard_button)
        var addButton = root.findViewById<Button>(R.id.dialog_reading_add_button)

        // Set up values
        header.text = getString(R.string.dialog_reading_header, book.book.title)
        //TODO: Animate `...` after the Header
        //TODO: When done reading, set header to the Time it took to read.

        timer.initTimer(604_800)

        startReadingButton.setOnClickListener {
            startButtonsContainer.visibility = GONE
            timerButtonContainer.visibility = VISIBLE
            timer.startTimer()
            playing = true
            setToggleDrawable()
        }

        isCancelable = false
        builder.setView(root)
        return builder.create()
    }

    private fun setToggleDrawable() {
        timerToggleButton.setImageDrawable(if (playing) pauseDrawable else playDrawable)
    }
}