package com.android.educo.views.auth

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.educo.R
import com.android.educo.databinding.ActivitySignUpBinding
import com.android.educo.utils.isValidEmail
import com.android.educo.utils.isValidPassword
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

lateinit var Dialog: ProgressDialog


class SignUpActivity : AppCompatActivity() {

    // Initialize SignUpActivity Data binding Object
    private lateinit var mBinding: ActivitySignUpBinding

    // initialize firebase authentication
    private lateinit var firebaseAuth: FirebaseAuth

    // Declare variables for Email and Password EditTextInput
    private lateinit var mEmailAddress: EditText
    private lateinit var mPassword: EditText
    private lateinit var mFullname: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialise databinding with signUp activity
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()

        // initialise email and password Input
        // variables from data binding object
        mEmailAddress = mBinding.edtEmailAddress
        mPassword = mBinding.edtPassword
        mFullname = mBinding.edtFullName

        // initialise onClick Events on Sign Up button
        initOnclickEvents()


        Dialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
        val current_user = firebaseAuth.currentUser

        if (current_user != null) {
            Toast.makeText(this, "USER EXIST" + current_user.email, Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this, "USER DOES NOT EXIST", Toast.LENGTH_LONG).show()
        }
    }

    private fun initOnclickEvents() {
        mBinding.signUpBtn.setOnClickListener {

            // getEmail and Password text from Editfield
            val mEmailText = mEmailAddress.text.toString()
            val mPasswordText = mPassword.text.toString()
            val mFullNameText = mFullname.text.toString()
            val valid = isValidEmailandPassInput(mFullNameText, mEmailText, mPasswordText)

            if (valid) {
                Dialog.setTitle("Email Sign Up")
                Dialog.setMessage("SignIn Up and Authenticating User")
                signUpwithEmailandPassword(mEmailText, mPasswordText)
                Dialog.show()
            } else Toast.makeText(this, "Invalid input Text", Toast.LENGTH_LONG).show()

        }


    }

    /*
     * This function signUp the User with Email and Password
     * SignIn the User after validating Email and Password
     *
     * @param email
     * @Param password
     *
     * @return it has no return value
    * */
    private fun signUpwithEmailandPassword(email: String, password: String) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d("TAG_SIGNUP_ACTIVITY", "" + task.result + task.exception)
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        Log.d("SIGNUP_ACTIVITY", "User signed in Successfully")
                        val user = firebaseAuth.currentUser
                        Dialog.dismiss()
                        Toast.makeText(this, "User Signed Up Successfully", Toast.LENGTH_LONG)
                            .show()
//                        TODO("Implement UpdateUI functionality and return $user to it")

                    } else {
                        // If sign in fails, display a message to the user.
                        Dialog.dismiss()
                        Log.d("SIGNUP_ACTIVITY", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

//                        TODO("Return null to the UpdateUI functionality")
                    }
                }
        } catch (E: ApiException) {
            Log.d("SIGNUP_ACTIVITY", "createUserWithEmail:failure " + E.message)
            Toast.makeText(
                this, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    /*
     * This function validate Users Email and Password
     *
     * @param email
     * @Param password
     *
     * @return true if email and password is valid
    * */
    private fun isValidEmailandPassInput(
        fullname: String,
        email: String,
        password: String
    ): Boolean {
        if (fullname.isEmpty()) {
            mFullname.error = getString(R.string.error_message_in_valid_name)
            return false
        }

        if (!email.isValidEmail()) {
            mEmailAddress.error = getString(R.string.error_message_in_valid_email)
            return false
        }
        if (!password.isValidPassword()) {
            mPassword.error = getString(R.string.error_message_short_password)
            return false
        }

        return true
    }

    // sign out the authenticated user
    private fun signOut() = FirebaseAuth.getInstance().signOut()

}