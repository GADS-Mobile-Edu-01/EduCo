package com.android.educo.views.main.ui.offline.vmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.educo.offline.OfflineDao

/**
 * Author: A. L. Zailani
 */
class OfflineViewModelFactory(
    private val dataSource: OfflineDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OfflineViewModel::class.java)) {
            return OfflineViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}