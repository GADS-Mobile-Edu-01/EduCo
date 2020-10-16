package com.android.educo.views.auth.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.educo.model.User
import com.android.educo.utils.Constants.COLLECTION_USERS
import com.android.educo.utils.PrefsUtil.setAdmin
import com.android.educo.utils.PrefsUtil.setUserName
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private val userRef = db.collection(COLLECTION_USERS)

    private val _isSuccessful = MutableLiveData<Boolean>().apply { value = false }
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _message = MutableLiveData<String>().apply { value = "" }
    val message: LiveData<String> = _message

    fun authUser(email: String, password: String) {
        _loading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            _loading.value = false
            if (it.isSuccessful)
                setCurrentUserName()
            else {
                _isSuccessful.value = false
                _message.value = it.exception?.message
            }
        }
    }

    private fun setCurrentUserName() {
        userRef.document(auth.currentUser?.uid!!).get().addOnCompleteListener {
            if(it.isSuccessful){
                val result = it.result?.toObject<User>()!!
                result.name.setUserName()
                result.isAdmin.setAdmin()
                _isSuccessful.value = it.isSuccessful
            }else{
                if(auth.currentUser != null){
                    auth.signOut()
                }
                _isSuccessful.value = false
                _message.value = it.exception?.message
            }
        }
    }
}