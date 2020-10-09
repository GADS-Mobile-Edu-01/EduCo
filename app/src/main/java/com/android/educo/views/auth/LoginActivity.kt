package com.android.educo.views.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.educo.R
import com.android.educo.databinding.ActivityLoginBinding
import com.android.educo.utils.isValidEmail
import com.android.educo.views.auth.viewModels.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        init()
    }

    private fun init() {
        mBinding.lifecycleOwner = this
        mBinding.model = viewModel
        mBinding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        mBinding.btnSignIn.setOnClickListener { signUp() }
        mBinding.btnSignUp.setOnClickListener {
            //  TODO: Add the code to navigate to Sign Up Screen.
        }

        viewModel.isSuccessful.observe(this, Observer { isSuccessful ->
            if (isSuccessful) {
                //  TODO: Add the code to navigate to the Dashboard.
            }
        })
        viewModel.message.observe(this, Observer { message ->
            if (message.isNotEmpty())
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })
    }

    private fun signUp() {
        val email = mBinding.edtEmail.text.toString().trim()
        val password = mBinding.edtPassword.text.toString().trim()

        if (validateFields(email, password))
            viewModel.authUser(email, password)
    }

    /**
     * This function validates form fields.
     *
     * @param email
     * @param password
     *
     * @return true if the form is valid or false if otherwise.
     */
    private fun validateFields(email: String, password: String): Boolean {
        if (!email.isValidEmail()) {
            mBinding.edtEmail.error = getString(R.string.error_message_in_valid_email)
            return false
        }

        if (password.length < 8) {
            mBinding.edtPassword.error = getString(R.string.error_message_short_password)
            return false
        }
        return true
    }
}