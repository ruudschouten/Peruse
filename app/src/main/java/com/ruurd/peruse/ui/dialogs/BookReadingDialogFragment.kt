package com.ruurd.peruse.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.data.repository.AppRepository
import com.ruurd.peruse.databinding.DialogReadingBookBinding
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.timer.State
import com.ruurd.peruse.ui.adapters.ReadingChapterRecyclerViewAdapter
import com.ruurd.peruse.ui.adapters.viewholders.ReadingChapterViewHolder
import com.ruurd.peruse.util.KeyboardUtil.hideKeyboard
import com.ruurd.peruse.util.NotificationUtil

class BookReadingDialogFragment(var book: FullBookPOJO) : DialogFragment() {

    private lateinit var chapterAdapter: ReadingChapterRecyclerViewAdapter
    private lateinit var firstChapter: Chapter
    private var addFirstChapter: Boolean = false

    private lateinit var pauseDrawable: Drawable
    private lateinit var playDrawable: Drawable

    private lateinit var appRepo: AppRepository

    private var startTime: Long = 0L

    private lateinit var binding: DialogReadingBookBinding
    private val readingBinding get() = binding.dialogReadingLayout
    private val finishedReadingBinding get() = binding.dialogFinishedReadingLayout
    private val incompleteBinding get() = readingBinding.dialogReadingFirstChapter
    private val timer get() = readingBinding.dialogReadingTimer

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }
        binding = DialogReadingBookBinding.inflate(LayoutInflater.from(requireContext()))

        NotificationUtil.createChannel(requireContext())

        appRepo = AppRepository(requireContext())

        setupDrawables()

        val builder = AlertDialog.Builder(requireContext())

        setupGeneralValues()
        setupTimerValues()
        setupFinishedValues()

        isCancelable = false
        builder.setView(binding.root)
        return builder.create()
    }

    private fun setupDrawables() {
        pauseDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_pause_24dp)!!
        playDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_play_arrow_24dp)!!
    }

    private fun setupGeneralValues() {
        binding.dialogReadingHeader.text = getString(R.string.reading_book_reading, book.book.title)

        readingBinding.dialogReadingStartButton.setOnClickListener {
            if (addFirstChapter) {
                val start = incompleteBinding.chapterIncompleteStart.text.toString().toInt()
                firstChapter =
                    Chapter(incompleteBinding.chapterIncompleteTitle.text.toString(), start, 0)
                chapterAdapter.setAt(0, firstChapter)

                finishedReadingBinding.dialogReadingChapterAmount.setText("1")

                binding.root.hideKeyboard()
            }
            startTime = System.currentTimeMillis()
            readingBinding.dialogReadingStartButtonContainer.visibility = GONE
            readingBinding.dialogReadingFirstChapterContainer.visibility = GONE
            readingBinding.dialogReadingTimerButtons.visibility = VISIBLE
            readingBinding.dialogReadingTimer.start()
            setToggleDrawable()

            NotificationUtil.make(
                requireContext(),
                getString(R.string.reading_book_reading, book.book.title),
                readingBinding.dialogReadingTimer
            )
        }

        readingBinding.dialogReadingCancelButton.setOnClickListener {
            closeDialog()
        }

        readingBinding.dialogReadingAddStartChapter.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                incompleteBinding.root.visibility = VISIBLE
            } else {
                incompleteBinding.root.visibility = GONE
            }
            addFirstChapter = isChecked
        }
    }

    private fun setupTimerValues() {
        readingBinding.dialogReadingTimerTogglePlayButton.setOnClickListener {
            if (timer.getState() == State.RUNNING) {
                timer.pause()
                NotificationUtil.updateStatus(
                    getString(
                        R.string.reading_book_paused,
                        book.book.title
                    )
                )
            } else {
                timer.start()
                NotificationUtil.updateStatus(
                    getString(
                        R.string.reading_book_reading,
                        book.book.title
                    )
                )
            }
            setToggleDrawable()
        }

        readingBinding.dialogReadingTimerStopButton.setOnClickListener {
            timer.stop()
            binding.dialogReadingHeader.text = timer.getFormattedTime()
            readingBinding.dialogReadingTimerContainer.visibility = GONE
            finishedReadingBinding.dialogReadingFinishedContainer.visibility = VISIBLE

            NotificationUtil.remove()
        }
    }

    private fun setupFinishedValues() {
        chapterAdapter = ReadingChapterRecyclerViewAdapter(mutableListOf(Chapter()))
        finishedReadingBinding.dialogReadingChapters.adapter = chapterAdapter
        finishedReadingBinding.dialogReadingChapters.layoutManager = LinearLayoutManager(activity)

        finishedReadingBinding.dialogReadingDiscardButton.setOnClickListener {
            timer.onDestroy()
            closeDialog()
        }

        finishedReadingBinding.dialogReadingAddButton.setOnClickListener {
            var totalPages = 0
            val chapters = mutableListOf<Chapter>()

            for (i in 0 until chapterAdapter.itemCount) {
                val viewHolder =
                    finishedReadingBinding.dialogReadingChapters.findViewHolderForAdapterPosition(i) as ReadingChapterViewHolder
                if (viewHolder.isAnyFieldEmpty()) {
                    Snackbar.make(
                        binding.root,
                        "One or more Chapters have missing values.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val chapter = viewHolder.getChapter()
                chapters.add(chapter)
                totalPages += chapter.pages
            }

            val totalDuration = timer.getTime()
            val durationPerPage = totalDuration / totalPages

            for (chapter: Chapter in chapters) {
                chapter.date = startTime
                chapter.duration = durationPerPage * chapter.pages
            }

            val book = book.toModel()
            book.chapters.addAll(chapters)

            appRepo.fullUpdate(book)

            timer.onDestroy()
            closeDialog()
        }

        finishedReadingBinding.dialogReadingChapterAmount.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                updateChaptersRead()
                false
            } else {
                true
            }
        }

        finishedReadingBinding.dialogReadingChapterAmount.addTextChangedListener(afterTextChanged = {
            updateChaptersRead()
        })
    }

    private fun updateChaptersRead() {
        try {
            val text = finishedReadingBinding.dialogReadingChapterAmount.text.toString()
            val count = text.toInt()
            updateChapterAdapter(count)
            enableAddButton()
        } catch (ex: NumberFormatException) {
            disableAddButton()
        }
    }

    private fun updateChapterAdapter(chapterCount: Int) {
        chapterAdapter.clear()
        for (i in 0 until chapterCount) {
            if (i == 0 && addFirstChapter) {
                chapterAdapter.add(firstChapter)
                continue
            }
            chapterAdapter.add(Chapter())
        }
        chapterAdapter.notifyDataSetChanged()
    }

    private fun enableAddButton() {
        finishedReadingBinding.dialogReadingAddButton.isEnabled = true
        finishedReadingBinding.dialogReadingAddButton.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimary
            )
        )
    }

    private fun disableAddButton() {
        finishedReadingBinding.dialogReadingAddButton.isEnabled = false
        finishedReadingBinding.dialogReadingAddButton.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.fontSecondaryColor
            )
        )
    }

    private fun setToggleDrawable() {
        readingBinding.dialogReadingTimerTogglePlayButton.setImageDrawable(
            if (timer.getState() == State.RUNNING) pauseDrawable
            else playDrawable
        )
    }

    private fun closeDialog() {
        NotificationUtil.remove()
        dialog?.cancel()
    }
}