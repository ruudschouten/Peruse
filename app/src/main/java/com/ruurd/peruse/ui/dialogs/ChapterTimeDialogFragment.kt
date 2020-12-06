package com.ruurd.peruse.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ruurd.peruse.R
import com.ruurd.peruse.databinding.DialogChapterTimeBinding
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.models.Chapter
import com.ruurd.peruse.util.TimeUtil

class ChapterTimeDialogFragment(private var book: Book) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogChapterTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (context == null) {
            throw IllegalStateException("Context can't be null when creating a dialog.")
        }

        binding = DialogChapterTimeBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )

        binding.dialogChapterCalculate.setOnClickListener {
            calculate()
        }

        binding.dialogChapterPagesEnd.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                calculate()
                false
            } else {
                true
            }
        }

        return binding.root
    }

    private fun calculate() {
        val startText = binding.dialogChapterPagesStart.text.toString()
        val endText = binding.dialogChapterPagesEnd.text.toString()

        if (startText.isEmpty() || endText.isEmpty()) {
            binding.dialogChapterStatus.text = getString(R.string.chapter_calc_fields_empty)
            return
        }
        val start = startText.toInt()
        val end = endText.toInt()
        if (end <= start) {
            binding.dialogChapterStatus.text = getString(R.string.chapter_calc_end_too_small)
            return
        } else if (start == 0) {
            binding.dialogChapterStatus.text = getString(R.string.chapter_calc_start_zero)
            return
        }

        val chapter = Chapter(start, end)
        val estimate = TimeUtil.toTime(chapter.estimatedDuration(book))
        val hours = estimate.hours
        val minutes = estimate.minutes
        val seconds = estimate.seconds
        if (hours > 0) {
            binding.dialogChapterStatus.text =
                getString(R.string.chapter_calc_result_full, hours, minutes, seconds)
        } else {
            binding.dialogChapterStatus.text =
                getString(R.string.chapter_calc_result, minutes, seconds)
        }
    }
}