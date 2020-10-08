package com.android.educo.views.main.ui.activities

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
            //  TODO: Add the code to navigate to Sign Up Screen.
        }
        mBinding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

            finish()
        }
    }
}