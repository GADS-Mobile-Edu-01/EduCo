package com.android.educo.views.main.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.educo.R
import com.android.educo.utils.PrefsUtil
import com.android.educo.utils.PrefsUtil.setAdmin
import com.android.educo.utils.PrefsUtil.setUserName
import com.android.educo.utils.Resource
import com.android.educo.views.BaseFragment
import com.android.educo.views.details.DetailsActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    val data = it.data
                    data.name.setUserName()
                    data.isAdmin.setAdmin()
                    initView()
                }
                is Resource.Loading -> {

                }
                is Resource.Failure -> {

                }
            }
        })
    }

    private fun initView() {
        catalogCard.setOnClickListener {
            findNavController().navigate(R.id.catalogueFragment)
        }
        offlineCard.setOnClickListener {
            findNavController().navigate(R.id.offlineFragment)
        }
        catalogueAddFab.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddCatalogueFragment())
        }

        if (PrefsUtil.isAdmin()) {
            catalogueAddFab.visibility = View.VISIBLE
        } else {
            catalogueAddFab.visibility = View.GONE
        }

        helloText.text = "Hello,\n${PrefsUtil.getUserName()}"

    }

}