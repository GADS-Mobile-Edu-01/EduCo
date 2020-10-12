package com.android.educo.views.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.educo.R
import com.android.educo.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        init()
    }

    private fun init() {
        mBinding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))

            finish()
        }
        mBinding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

            finish()
        }
    }
}