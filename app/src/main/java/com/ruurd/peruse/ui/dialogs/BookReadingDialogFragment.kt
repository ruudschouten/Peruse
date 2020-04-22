package com.ruurd.peruse.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.nfc.FormatException
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.data.repository.AppRepository
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.timer.State
import com.ruurd.peruse.ui.adapters.ReadingChapterRecyclerViewAdapter
import com.ruurd.peruse.ui.adapters.ReadingChapterViewHolder
import kotlinx.android.synthetic.main.dialog_reading_book.view.*
import kotlinx.android.synthetic.main.dialog_reading_book_finished.view.*
import kotlinx.android.synthetic.main.dialog_reading_book_finished.view.dialog_reading_add_button
import kotlinx.android.synthetic.main.dialog_reading_book_finished.view.dialog_reading_discard_button
import kotlinx.android.synthetic.main.dialog_reading_book_timer.view.*
import java.lang.Exception

class BookReadingDialogFragment(var book: FullBookPOJO) : DialogFragment() {

    private lateinit var root: View

    private lateinit var chapterAdapter: ReadingChapterRecyclerViewAdapter

    private lateinit var pauseDrawable: Drawable
    private lateinit var playDrawable: Drawable

    private lateinit var appRepo: AppRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        appRepo = AppRepository(context!!)

        setupDrawables()

        val builder = AlertDialog.Builder(context!!)
        val inflater = requireActivity().layoutInflater
        root = inflater.inflate(R.layout.dialog_reading_book, null)

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

        root.dialog_reading_add_start_chapter.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                root.dialog_reading_first_chapter.visibility = VISIBLE
            } else {
                root.dialog_reading_first_chapter.visibility = GONE
            }
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
            root.dialog_reading_header.text = root.dialog_reading_timer.getFormattedTime()
            root.dialog_reading_timer_container.visibility = GONE
            root.dialog_reading_finished_container.visibility = VISIBLE
        }
    }

    private fun setupFinishedValues() {
        chapterAdapter = ReadingChapterRecyclerViewAdapter(mutableListOf(Chapter()))
        root.dialog_reading_chapters.adapter = chapterAdapter
        root.dialog_reading_chapters.layoutManager = LinearLayoutManager(activity)

        root.dialog_reading_discard_button.setOnClickListener {
            root.dialog_reading_timer.onDestroy()
            dialog?.cancel()
        }

        root.dialog_reading_add_button.setOnClickListener {
            var totalPages = 0
            val chapters = mutableListOf<Chapter>()

            for (i in 0 until chapterAdapter.itemCount) {
                val viewHolder = root.dialog_reading_chapters.findViewHolderForAdapterPosition(i) as ReadingChapterViewHolder
                if(viewHolder.isAnyFieldEmpty()) {
                    Snackbar.make(root, "One or more Chapters have missing values.", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val chapter = viewHolder.getChapter()
                chapters.add(chapter)
                totalPages += chapter.pages
            }

            val totalDuration = root.dialog_reading_timer.getTime()
            val durationPerPage = totalDuration / totalPages

            for (chapter: Chapter in chapters) {
                chapter.duration = durationPerPage * chapter.pages
            }

            val book = book.toModel()
            book.chapters.addAll(chapters)

            appRepo.fullUpdate(book)

            root.dialog_reading_timer.onDestroy()
            dialog?.cancel()
        }

        root.dialog_reading_chapter_amount.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                try {
                    val text = v.text.toString()
                    val count = text.toInt()
                    updateChapterAdapter(count)
                    enableAddButton()
                } catch (ex: NumberFormatException) {
                    disableAddButton()
                }
                true
            } else {
                false
            }
        }
        root.dialog_reading_chapter_amount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (root.dialog_reading_chapter_amount.text.toString().isEmpty()) {
                    disableAddButton()
                }
            }
        }
    }

    private fun updateChapterAdapter(chapterCount: Int) {
        chapterAdapter.entries.clear()
        for (i in 0 until chapterCount) {
            chapterAdapter.add(Chapter())
        }
        chapterAdapter.notifyDataSetChanged()
    }

    private fun enableAddButton() {
        root.dialog_reading_add_button.isEnabled = true
        root.dialog_reading_add_button.setTextColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorPrimary
            )
        )
    }

    private fun disableAddButton() {
        root.dialog_reading_add_button.isEnabled = false
        root.dialog_reading_add_button.setTextColor(
            ContextCompat.getColor(
                context!!,
                R.color.fontSecondaryColor
            )
        )
    }

    private fun setToggleDrawable() {
        root.dialog_reading_timer_toggle_play_button.setImageDrawable(
            if (root.dialog_reading_timer.getState() == State.RUNNING) pauseDrawable
            else playDrawable
        )
    }
}