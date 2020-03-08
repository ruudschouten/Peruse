package com.ruurd.peruse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ruurd.peruse.R
import com.ruurd.peruse.ui.fragments.viewmodels.SeriesViewModel

class SeriesFragment : Fragment() {

    private lateinit var seriesViewModel: SeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        seriesViewModel = ViewModelProvider(this).get(SeriesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_series, container, false)
        val textView: TextView = root.findViewById(R.id.text_series)
        seriesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
