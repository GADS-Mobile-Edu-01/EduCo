package com.android.educo.views.main.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.educo.model.User
import com.android.educo.network.FirebaseRepository
import com.android.educo.utils.Resource

class MainViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    val userDetails = MutableLiveData<Resource<User>>()

    fun getUserDetails(){
        userDetails.value = Resource.Loading()

    }
}