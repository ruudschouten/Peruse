package com.ruurd.peruse.ui.writers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WritersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Writers Fragment"
    }
    val text: LiveData<String> = _text
}