package com.ruurd.peruse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ruurd.peruse.R
import com.ruurd.peruse.ui.fragments.viewmodels.SeriesViewModel
import kotlinx.android.synthetic.main.fragment_series.view.*

class SeriesFragment : Fragment() {

    private lateinit var seriesViewModel: SeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        seriesViewModel = ViewModelProvider(this).get(SeriesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_series, container, false)
        seriesViewModel.text.observe(viewLifecycleOwner, Observer {
            root.text_series.text = it
        })
        return root
    }
}
