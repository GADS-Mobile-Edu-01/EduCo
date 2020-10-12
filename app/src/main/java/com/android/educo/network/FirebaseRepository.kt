package com.android.educo.network

import android.util.Log
import com.android.educo.model.User
import com.android.educo.utils.Constants
import com.android.educo.utils.Constants.COLLECTION_USERS
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

class FirebaseRepository {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    private val userRef = db.collection(COLLECTION_USERS)


    suspend fun getRemoteUser(): Resource<User?> {
        return try {
            val userSnapshot = userRef.document(auth.currentUser?.uid!!).get().await()
            val user: User? = userSnapshot.toObject()
            Resource.Success(user, "User remote details fetched successfully")
        } catch (e: Exception) {
            Resource.Failure(e.message!!)
        }
    }
}