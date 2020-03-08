package com.ruurd.peruse.ui.writers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ruurd.peruse.R

class WritersFragment : Fragment() {

    private lateinit var writersViewModel: WritersViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        writersViewModel =
                ViewModelProviders.of(this).get(WritersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_writers, container, false)
        val textView: TextView = root.findViewById(R.id.text_writers)
        writersViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
