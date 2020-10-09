package com.android.educo.views.auth.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _isSuccessful = MutableLiveData<Boolean>().apply { value = false }
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _message = MutableLiveData<String>().apply { value = "" }
    val message: LiveData<String> = _message

    fun authUser(email: String, password: String) {
        _loading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            _loading.value = false
            if (it.isSuccessful)
                _isSuccessful.value = it.isSuccessful
            else {
                _isSuccessful.value = false
                _message.value = it.exception?.message
            }
        }
    }
}