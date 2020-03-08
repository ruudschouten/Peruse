package com.ruurd.peruse.ui.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeriesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Series Fragment"
    }
    val text: LiveData<String> = _text
}