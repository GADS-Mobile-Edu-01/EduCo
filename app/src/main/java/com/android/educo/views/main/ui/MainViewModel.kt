package com.android.educo.views.main.ui

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.educo.model.Catalogue
import com.android.educo.model.User
import com.android.educo.network.FirebaseRepository
import com.android.educo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    val materialUpload = MutableLiveData<Resource<String>>()
    val user = MutableLiveData<Resource<User>>()


    fun uploadMaterial(catalogue: Catalogue, uri: Uri){
        materialUpload.value = Resource.Loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = repository.addNewCatalogue(catalogue, uri)
                materialUpload.postValue(data)
            }
        }
    }

    fun getUser(){
        user.value = Resource.Loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = repository.getRemoteUser()
                user.postValue(data)
            }
        }
    }
}