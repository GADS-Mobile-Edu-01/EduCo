package com.android.educo.views.details

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.educo.R
import com.android.educo.databinding.ActivityDetailsBinding
import com.android.educo.model.Catalogue
import com.android.educo.offline.OfflineRoomDatabase
import com.android.educo.utils.Constants
import com.android.educo.utils.Constants.COLLECTION_AUDIO
import com.android.educo.utils.Constants.COLLECTION_DOCUMENTS
import com.android.educo.utils.Constants.COLLECTION_VIDEO
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File

/**
 * Author: A. L. Zailani
 */
class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var mBinding: ActivityDetailsBinding

    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        init()
    }

    private fun init() {
        val dataSource = OfflineRoomDatabase.getInstance(application).offlineDao
        val viewModelFactory = DetailsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)

        intent?.let {
            val key = it.getStringExtra("material_key").toString()
            val type = it.getStringExtra("material_type").toString()
            val isOffline = it.getBooleanExtra("is_offline", false)

            file = getExternalFilesDir("EdoCo/${type}s/")!!

            if (isOffline)
                fetchMaterial(key)
            else
                fetchMaterial(key, type)
        }

        mBinding.lifecycleOwner = this
        mBinding.model = viewModel
        mBinding.content.model = viewModel

        viewModel.downloaded.observe(this, Observer {
            it?.let { isDownloaded ->
                if (isDownloaded) {
                    viewModel.resetDownloaded()

                    Snackbar.make(
                        mBinding.fabDownload,
                        "Material successfully download!\nFind listed under you offline Materials.",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Action", null).show()
                }
            }
        })
    }

    private fun fetchMaterial(key: String) {
        viewModel.fetch(key)
    }

    private fun fetchMaterial(key: String, type: String) {
        viewModel.fetch(key, type)
    }

    fun download(v: View) {
        file.let {
            viewModel.download(file.path)
        }
    }

    fun back(v: View) {
        onBackPressed()
    }
}
