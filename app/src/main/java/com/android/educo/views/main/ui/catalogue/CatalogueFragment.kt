package com.android.educo.views.main.ui.catalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.educo.R
import com.android.educo.views.BaseFragment
import com.android.educo.views.main.ui.catalogue.adapter.CatalogueTabAdapter
import com.android.educo.views.main.ui.catalogue.tabs.CatalogueAudioFragment
import com.android.educo.views.main.ui.catalogue.tabs.CatalogueDocsFragment
import com.android.educo.views.main.ui.catalogue.tabs.CatalogueVideoFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_catalogue.*

class CatalogueFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    private fun setUpData() {
        val fragments = ArrayList<Fragment>()
        fragments.add(CatalogueVideoFragment())
        fragments.add(CatalogueAudioFragment())
        fragments.add(CatalogueDocsFragment())
        val adapter = CatalogueTabAdapter(this)
        adapter.fragments = fragments
        catalogueViewPager.adapter = adapter
        TabLayoutMediator(catalogueTabLayout, catalogueViewPager) { tab, position ->
            when(position){
                0 -> {
                    tab.text = "Video"
                    tab.icon = requireContext().resources.getDrawable(R.drawable.ic_video_ic)
                }
                1 -> {
                    tab.text = "Audio"
                    tab.icon = requireContext().resources.getDrawable(R.drawable.ic_audio)
                }
                2 -> {
                    tab.text = "Documents"
                    tab.icon = requireContext().resources.getDrawable(R.drawable.ic_document)
                }
            }
        }.attach()
    }

}