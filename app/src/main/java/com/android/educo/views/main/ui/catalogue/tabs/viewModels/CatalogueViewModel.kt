package com.android.educo.views.main.ui.catalogue.tabs.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.educo.model.Catalogue
import com.android.educo.model.TextDocument
import com.android.educo.network.FirebaseRepository
import com.android.educo.utils.Resource
import kotlinx.coroutines.*
import org.w3c.dom.Text

class CatalogueViewModel : ViewModel() {

    val catalogue = MutableLiveData<Resource<ArrayList<Catalogue>>>()
    private val repository = FirebaseRepository()

    private val _textData = MutableLiveData<List<TextDocument>>().apply {
        value =  getTextData()
    }

    val textData: LiveData<List<TextDocument>>
        get() = _textData

    private fun getTextData(): List<TextDocument> {
        var data:List<TextDocument> = emptyList()
         viewModelScope.launch {
            data = querytextDoc()
        }
        return data
    }

    fun getCatalogue(type : String){
        catalogue.value = Resource.Loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = repository.getCatalogue(type)
                catalogue.postValue(data)
            }
        }
    }

    private suspend fun querytextDoc():List<TextDocument>{
        var data:List<TextDocument> = emptyList()
        withContext(Dispatchers.IO){
            data  = arrayListOf<TextDocument>(
                TextDocument(
                    "Intro to Java",
                    "Learn Java in 2 days",
                    "All you need to know about java",
                    "1:20mins"
                ),
                TextDocument(
                    "Intro to SQL",
                    "Learn SQL in 2 days",
                    "All you need to know about SQL",
                    "2:20mins"
                )
            )
        }

        return data
    }
}