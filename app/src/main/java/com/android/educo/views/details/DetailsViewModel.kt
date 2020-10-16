package com.android.educo.views.details

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _downloaded = MutableLiveData<Boolean>().apply { value = false }
    val downloaded: LiveData<Boolean> = _downloaded

    fun download(materialID: String) {
        _loading.value = true
        resetDownloaded()

        Handler().postDelayed({
            _loading.value = false
            _downloaded.value = true
        }, 2500)
    }

    fun resetDownloaded() {
        _downloaded.value = false
    }
}