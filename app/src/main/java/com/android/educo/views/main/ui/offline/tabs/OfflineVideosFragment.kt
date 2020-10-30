package com.android.educo.views.main.ui.offline.tabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.educo.databinding.FragmentOfflineVideosBinding
import com.android.educo.offline.OfflineRoomDatabase
import com.android.educo.views.main.ui.offline.adapters.OfflineMaterialsAdapter
import com.android.educo.views.main.ui.offline.vmodel.OfflineViewModel
import com.android.educo.views.main.ui.offline.vmodel.OfflineViewModelFactory

/**
 * Author: A. L. Zailani
 */
class OfflineVideosFragment : Fragment() {
    private lateinit var viewModel: OfflineViewModel
    private lateinit var mBinding: FragmentOfflineVideosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentOfflineVideosBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = OfflineRoomDatabase.getInstance(application).offlineDao
        val viewModelFactory = OfflineViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OfflineViewModel::class.java)

        mBinding.lifecycleOwner = this
        mBinding.viewModel = viewModel

        mBinding.rvRecords.adapter = OfflineMaterialsAdapter()

        viewModel.videos().observe(viewLifecycleOwner, {
            Log.d("Response", it.toString())
            it?.let { list ->
                mBinding.llEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                (mBinding.rvRecords.adapter as OfflineMaterialsAdapter).materials(list)
            }
        })

        return mBinding.root
    }

}