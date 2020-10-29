package com.android.educo.views.main.ui.offline.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.android.educo.offline.Offline
import com.android.educo.offline.OfflineDao

/**
 * Author: A. L. Zailani
 */
class OfflineViewModel(private val db: OfflineDao, application: Application) :
    AndroidViewModel(application) {

    fun audios(): LiveData<List<Offline>> = db.audios()

    fun videos(): LiveData<List<Offline>> = db.videos()

    fun documents(): LiveData<List<Offline>> = db.documents()
}