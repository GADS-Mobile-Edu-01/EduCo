package com.android.educo.network

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.android.educo.model.Catalogue
import com.android.educo.model.User
import com.android.educo.utils.Constants
import com.android.educo.utils.Constants.AUDIO
import com.android.educo.utils.Constants.COLLECTION_AUDIO
import com.android.educo.utils.Constants.COLLECTION_DOCUMENTS
import com.android.educo.utils.Constants.COLLECTION_USERS
import com.android.educo.utils.Constants.COLLECTION_VIDEO
import com.android.educo.utils.Constants.VIDEO
import com.android.educo.utils.PrefsUtil.setUserName
import com.android.educo.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File

class FirebaseRepository {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    private val userRef = db.collection(COLLECTION_USERS)
    private val audioRef = db.collection(COLLECTION_AUDIO)
    private val videoRef = db.collection(COLLECTION_VIDEO)
    private val docsRef = db.collection(COLLECTION_DOCUMENTS)




    suspend fun getRemoteUser(): Resource<User> {
        return try {
            val userSnapshot = userRef.document(auth.currentUser?.uid!!).get().await()
            val user: User = userSnapshot.toObject()!!
            Resource.Success(user, "User remote details fetched successfully")
        } catch (e: Exception) {
            Resource.Failure(e.message!!)
        }
    }

    suspend fun addNewCatalogue(catalogue: Catalogue, uri : Uri): Resource<String> {
        return try {
            val uploadTask = storageRef.child("catalogueFiles/${uri.toFile().name}").putFile(uri)
            val downloadUrl = uploadTask.await().storage.downloadUrl.await()
            catalogue.downloadLink = downloadUrl.toString()
            val snapshot = when (catalogue.type) {
                VIDEO -> {
                    val id = videoRef.document().id
                    catalogue.id = id
                    videoRef.document(id).set(catalogue).await()
                }
                AUDIO -> {
                    val id = audioRef.document().id
                    catalogue.id = id
                    audioRef.document(id).set(catalogue).await()
                }
                else -> {
                    val id = docsRef.document().id
                    catalogue.id = id
                    docsRef.document(id).set(catalogue).await()
                }
            }
            Resource.Success("Material uploaded successfully", "Material uploaded successfully")
        } catch (e: Exception) {
            Resource.Failure(e.message!!)
        }
    }

}