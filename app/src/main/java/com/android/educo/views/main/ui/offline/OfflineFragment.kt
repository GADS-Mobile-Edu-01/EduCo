package com.android.educo.views.main.ui.offline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.educo.R
import com.android.educo.views.main.ui.offline.adapters.OfflineTabsAdapter
import com.android.educo.views.main.ui.offline.tabs.OfflineAudioFragment
import com.android.educo.views.main.ui.offline.tabs.OfflineDocsFragment
import com.android.educo.views.main.ui.offline.tabs.OfflineVideosFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_offline.*

class OfflineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    private fun setUpData() {
        val fragments = ArrayList<Fragment>()
        fragments.add(OfflineVideosFragment())
        fragments.add(OfflineAudioFragment())
        fragments.add(OfflineDocsFragment())
        val adapter = OfflineTabsAdapter(this)
        adapter.fragments = fragments
        offlineViewPager.adapter = adapter
        TabLayoutMediator(offlineTabLayout, offlineViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.label_videos)
                    tab.icon = requireContext().resources.getDrawable(R.drawable.ic_video_ic)
                    context?.let { ContextCompat.getColor(it, R.color.colorWhite) }?.let {
                        tab.icon?.setTint(
                            it
                        )
                    }
                }
                1 -> {
                    tab.text = getString(R.string.label_audios)
                    tab.icon = requireContext().resources.getDrawable(R.drawable.ic_audio)
                }
                2 -> {
                    tab.text = getString(R.string.label_documents)
                    tab.icon = requireContext().resources.getDrawable(R.drawable.ic_document)
                }
            }
        }.attach()

        offlineTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIconColor = context?.let { ContextCompat.getColor(it, R.color.colorWhite) }!!
                tab?.icon?.setTint(tabIconColor)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabIconColor =
                    context?.let { ContextCompat.getColor(it, android.R.color.darker_gray) }!!
                tab?.icon?.setTint(tabIconColor)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}