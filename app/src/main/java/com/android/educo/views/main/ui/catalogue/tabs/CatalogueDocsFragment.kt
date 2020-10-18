package com.android.educo.views.main.ui.catalogue.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.educo.R
import com.android.educo.model.TextDocument
import com.android.educo.views.BaseFragment
import com.android.educo.views.main.ui.catalogue.adapter.DocRecyclerAdapter
import com.android.educo.views.main.ui.catalogue.tabs.viewModels.DocViewModel

class CatalogueDocsFragment : BaseFragment() {

    private lateinit var textDocRecyclerView:RecyclerView
    private val docTextViewModel: DocViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_catalogue_docs, container, false)

        textDocRecyclerView = view.findViewById(R.id.recycler_view) as RecyclerView

        docTextViewModel.textData.observe(viewLifecycleOwner, Observer { textData ->
            if(textData.isNotEmpty()){
                val textDocAdapter = DocRecyclerAdapter(requireContext(), textData)
                textDocRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                textDocRecyclerView.adapter = textDocAdapter
            }
        })

        // Inflate the layout for this fragment
        return view
    }

}