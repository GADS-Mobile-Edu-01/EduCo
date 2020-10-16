package com.android.educo.views.details

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.educo.R
import com.android.educo.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        init()
    }

    private fun init() {
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

    fun download(v: View) {
        viewModel.download("")
    }

    fun back(v: View) {
        onBackPressed()
    }
}