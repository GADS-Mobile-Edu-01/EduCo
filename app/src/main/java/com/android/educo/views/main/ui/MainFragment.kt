package com.android.educo.views.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.educo.R
import com.android.educo.utils.PrefsUtil
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        catalogCard.setOnClickListener {
            findNavController().navigate(R.id.catalogueFragment)
        }
        offlineCard.setOnClickListener {
            findNavController().navigate(R.id.offlineFragment)
        }

        helloText.text = "Hello,\n${PrefsUtil.getUserName()}"

    }

}