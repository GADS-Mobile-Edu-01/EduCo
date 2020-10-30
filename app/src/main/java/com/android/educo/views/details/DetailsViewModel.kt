package com.android.educo.views.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.educo.model.Catalogue
import com.android.educo.offline.Offline
import com.android.educo.offline.OfflineDao
import com.android.educo.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.File

class DetailsViewModel(private val db: OfflineDao, application: Application) :
    AndroidViewModel(application) {
    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _downloaded = MutableLiveData<Boolean>().apply { value = false }
    val downloaded: LiveData<Boolean> = _downloaded

    private val _material = MutableLiveData<Catalogue>()
    val material: LiveData<Catalogue> = _material

    fun fetch(key: String): LiveData<Offline> = db.get(key)

    fun fetch(key: String, type: String) {
        val db = Firebase.firestore
        val ref = db.collection(
            when (type) {
                "Audio" -> Constants.COLLECTION_AUDIO
                "Video" -> Constants.COLLECTION_VIDEO
                else -> Constants.COLLECTION_DOCUMENTS
            }
        )
        ref.document(key).get().addOnSuccessListener {
            _material.value = it.toObject(Catalogue::class.java)
        }.addOnFailureListener {
            Log.e("Response", "Error getting material:", it)
        }
    }

    fun download(path: String) {
        _loading.value = true
        resetDownloaded()

        val matVal = material.value!!

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl(matVal.downloadLink)

        val filePath = File(path)
        if (!filePath.exists())
            filePath.mkdirs()

        val file = File(filePath, matVal.name)

        storageRef.getFile(file).addOnSuccessListener {
            if (file.canRead()) {
                _loading.value = false
                _downloaded.value = true

                val offline = Offline(
                    id = matVal.id,
                    type = matVal.type,
                    title = matVal.title,
                    description = matVal.description,
                    downloadLink = file.path
                )

                insert(offline)
            }
        }.addOnFailureListener {
            Log.e("Response", "Error download material:", it)
        }
    }

    private fun insert(offline: Offline) {
        viewModelScope.launch {
            save(offline)
        }
    }

    fun resetDownloaded() {
        _downloaded.value = false
    }

    private suspend fun save(offline: Offline) {
        db.insert(offline)
    }

}