package com.android.educo.views.main.ui.catalogue.tabs.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.educo.model.TextDocument
import kotlinx.coroutines.*
import org.w3c.dom.Text

class DocViewModel:ViewModel() {

    private val _textData = MutableLiveData<List<TextDocument>>().apply {
        value =  getTextData()
    }

    val textData: LiveData<List<TextDocument>>
        get() = _textData

    private fun getTextData(): List<TextDocument> {
        var data:List<TextDocument> = emptyList()
        viewModelScope.launch {
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