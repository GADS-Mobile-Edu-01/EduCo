package com.android.educo.views.main.ui.catalogue.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CatalogueTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    var fragments = ArrayList<Fragment>()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}