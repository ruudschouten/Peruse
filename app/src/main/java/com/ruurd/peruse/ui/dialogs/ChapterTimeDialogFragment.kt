package com.ruurd.peruse.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ruurd.peruse.R
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.util.TimeUtil
import kotlinx.android.synthetic.main.dialog_chapter_time.view.*

class ChapterTimeDialogFragment(var book: Book) : BottomSheetDialogFragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        root = inflater.inflate(R.layout.dialog_chapter_time, container, false)

        root.dialog_chapter_calculate.setOnClickListener {
            calculate()
        }

        root.dialog_chapter_pages_end.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                calculate()
                false
            } else {
                true
            }
        }

        return root
    }

    private fun calculate() {
        val startText = root.dialog_chapter_pages_start.text.toString()
        val endText = root.dialog_chapter_pages_end.text.toString()

        if (startText.isEmpty() || endText.isEmpty()) {
            root.dialog_chapter_status.text = getString(R.string.chapter_calc_fields_empty)
            return
        }
        val start = startText.toInt()
        val end = endText.toInt()
        if (end <= start) {
            root.dialog_chapter_status.text = getString(R.string.chapter_calc_end_too_small)
            return
        } else if (start == 0) {
            root.dialog_chapter_status.text = getString(R.string.chapter_calc_start_zero)
            return
        }

        val chapter = Chapter(start, end)
        val estimate = TimeUtil.toTime(chapter.estimatedDuration(book))
        val hours = estimate.hours
        val minutes = estimate.minutes
        val seconds = estimate.seconds
        if (hours > 0) {
            root.dialog_chapter_status.text =
                getString(R.string.chapter_calc_result_full, hours, minutes, seconds)
        } else {
            root.dialog_chapter_status.text =
                getString(R.string.chapter_calc_result, minutes, seconds)
        }
    }
}