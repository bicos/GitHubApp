package com.ravy.common_ui.viewModel

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _navigateUrl = MutableLiveData<Uri>()
    val navigateUrl: LiveData<Uri> = _navigateUrl

    fun navigateUrl(url: String?) {
        try {
            url?.let { _navigateUrl.value = it.toUri() }
        } catch (e: Throwable) {
            Log.e("toUri", e.toString())
        }
    }
}