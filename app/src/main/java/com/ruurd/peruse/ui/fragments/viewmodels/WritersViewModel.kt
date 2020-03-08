package com.ruurd.peruse.ui.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WritersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Writers Fragment"
    }
    val text: LiveData<String> = _text
}