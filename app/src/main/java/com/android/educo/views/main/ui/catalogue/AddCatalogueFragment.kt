package com.android.educo.views.main.ui.catalogue

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.android.educo.R
import com.android.educo.model.Catalogue
import com.android.educo.utils.Constants.AUDIO
import com.android.educo.utils.Constants.DOCS
import com.android.educo.utils.Constants.FILE_SELECT_CODE
import com.android.educo.utils.Constants.VIDEO
import com.android.educo.utils.Resource
import com.android.educo.views.BaseFragment
import com.android.educo.views.main.ui.MainViewModel
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_catalogue.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.File


class AddCatalogueFragment : BaseFragment(), PickiTCallbacks {

    private lateinit var title : String
    private lateinit var description : String
    private var uri : Uri? = null
    private lateinit var type : String
    private lateinit var pickIt : PickiT
    private val viewModel : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_catalogue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().bottomNavigationView.visibility = View.GONE
        addBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        pickIt = PickiT(requireContext(),this,requireActivity())

        val categories = listOf(AUDIO, VIDEO, DOCS)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_drop_down,categories)
        addCatalogueCatEdit.setAdapter(arrayAdapter)
        uploadMaterialButton.setOnClickListener {
            checkStoragePermission()
        }
        uploadCatalogueButton.setOnClickListener {
            checkValidation()
        }

        setObservers()
    }

    private fun setObservers() {
        viewModel.materialUpload.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success-> {
                    hideProgress()
                    showToast(it.message)
                    requireActivity().onBackPressed()
                }
                is Resource.Failure -> {
                    hideProgress()
                    showToast(it.message)
                }
            }
        })
    }

    private fun hideProgress() {
        addProgress.visibility = View.GONE
    }

    private fun showProgress() {
        addProgress.visibility = View.VISIBLE

    }

    private fun checkValidation() {
        title = addMaterialTitleEdit.text.toString()
        description = addMaterialDescriptionEdit.text.toString()
        type = addCatalogueCatEdit.text.toString()

        if(addMaterialTitleEdit.text.isNullOrBlank()){
            addMaterialTitleLayout.error = getString(R.string.field_req)
            return
        }else{
            addMaterialTitleLayout.error = ""
        }
        if(addMaterialDescriptionEdit.text.isNullOrBlank()){
            addMaterialDescriptionLayout.error = getString(R.string.field_req)
            return
        }else{
            addMaterialDescriptionLayout.error = ""
        }
        if(addCatalogueCatEdit.text.isNullOrBlank()){
            addCatalogueCatLayout.error = getString(R.string.field_req)
            return
        }else{
            addCatalogueCatLayout.error = ""
        }
        val catalogue = Catalogue()
        catalogue.title = title
        catalogue.description = description
        catalogue.type = type
        if(uri == null){
            showToast("Please select a file")
            return
        }
        viewModel.uploadMaterial(catalogue, uri!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                FILE_SELECT_CODE -> {
                    val uri = data!!.data!!
                    pickIt.getPath(uri, Build.VERSION.SDK_INT)
                }
            }
        }
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                FILE_SELECT_CODE
            )
        } catch (ex: ActivityNotFoundException) {
            // Potentially direct the user to the Market with a Dialog
            showToast("Please Install a file manager")
        }
    }

    override fun PickiTonUriReturned() {

    }

    override fun PickiTonStartListener() {

    }

    override fun PickiTonProgressUpdate(progress: Int) {

    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        if(wasSuccessful){
            uri = File(path!!).toUri()
            uploadMaterialButton.text = "Attached : ${File(path!!).name}"
            showToast("File Attached")
        }else{
            if(Reason != null){
                showToast(Reason)
            }else{
                showToast( "File Attach error")
            }
        }
    }

    private fun checkStoragePermission() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        Permissions.check(
            requireContext() /*context*/,
            permissions,
            null /*rationale*/,
            null /*options*/,
            object : PermissionHandler() {
                override fun onGranted() {
                    showFileChooser()
                }
                override fun onBlocked(
                    context: Context?,
                    blockedList: java.util.ArrayList<String>?
                ): Boolean {
                    showToast("Please grant storage access")
                    return super.onBlocked(context, blockedList)
                }

                override fun onDenied(context: Context?, deniedPermissions: java.util.ArrayList<String>?) {
                    showToast("Please grant storage access")
                    super.onDenied(context, deniedPermissions)
                }

                override fun onJustBlocked(
                    context: Context?,
                    justBlockedList: java.util.ArrayList<String>?,
                    deniedPermissions: java.util.ArrayList<String>?
                ) {
                    showToast("Please grant storage access")
                    super.onJustBlocked(context, justBlockedList, deniedPermissions)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().bottomNavigationView.visibility = View.VISIBLE

    }


}