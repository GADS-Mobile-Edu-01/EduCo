package com.android.educo.views.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.educo.R
import com.android.educo.databinding.ActivityForgotPasswordBinding
import com.android.educo.utils.isValidEmail

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        initView()
    }

    private fun initView() {
        mBinding.backBtn.setOnClickListener {
            onBackPressed()
        }
        mBinding.btnForgotPassword.setOnClickListener {
            sendForgotMail()
        }
    }

    private fun sendForgotMail() {
        val email = mBinding.edtForgotEmail.text.toString()
        if(email.isValidEmail()){
            startForgotProcess()
        }else{
            mBinding.edtForgotEmail.error = getString(R.string.error_message_in_valid_email)
        }
    }

    private fun startForgotProcess() {
       // TODO : Start Forgot Process here
    }
}