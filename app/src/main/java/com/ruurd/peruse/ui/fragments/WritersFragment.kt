package com.ruurd.peruse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ruurd.peruse.R
import com.ruurd.peruse.ui.fragments.viewmodels.WritersViewModel
import kotlinx.android.synthetic.main.fragment_authors.view.*

class WritersFragment : Fragment() {

    private lateinit var writersViewModel: WritersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        writersViewModel = ViewModelProvider(this).get(WritersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_authors, container, false)
        writersViewModel.text.observe(viewLifecycleOwner, Observer {
            root.text_writers.text = it
        })
        return root
    }
}
