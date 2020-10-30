package com.android.educo.views.main.ui.offline.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Author: A. L. Zailani
 */
class OfflineTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var fragments = ArrayList<Fragment>()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}