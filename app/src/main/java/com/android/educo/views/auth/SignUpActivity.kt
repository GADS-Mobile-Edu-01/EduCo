package com.android.educo.views.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.educo.R
import com.android.educo.databinding.ActivitySignUpBinding
import com.android.educo.model.User
import com.android.educo.utils.Constants
import com.android.educo.utils.PrefsUtil.setAdmin
import com.android.educo.utils.PrefsUtil.setUserName
import com.android.educo.utils.isValidEmail
import com.android.educo.utils.isValidPassword
import com.android.educo.views.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

lateinit var Dialog: ProgressDialog


class SignUpActivity : AppCompatActivity() {

    // Initialize SignUpActivity Data binding Object
    private lateinit var mBinding: ActivitySignUpBinding

    // initialize firebase authentication
    private lateinit var firebaseAuth: FirebaseAuth

    // Declare variables for Email and Password EditTextInput
    private lateinit var mEmailAddress:EditText
    private lateinit var mPassword:EditText
    private lateinit var mFullname:EditText
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var userRef : CollectionReference

    // Declare variable for GoogleSignInOptions  and GoogleSignInClient
    private lateinit var GsignInOption:GoogleSignInOptions
    private lateinit var GsignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
        auth = FirebaseAuth.getInstance()
        userRef = db.collection(Constants.COLLECTION_USERS)
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
//        val current_user = firebaseAuth.currentUser

//        if (current_user != null){
//            Toast.makeText(this, "USER EXIST"+current_user.email, Toast.LENGTH_LONG).show()
//
//        }else {
//            Toast.makeText(this, "USER DOES NOT EXIST", Toast.LENGTH_LONG).show()
//        }
    }

    private fun initOnclickEvents() {
        mBinding.signUpBtn.setOnClickListener {

            // getEmail and Password text from Editfield
            val mEmailText = mEmailAddress.text.toString()
            val mPasswordText = mPassword.text.toString()
            val mFullNameText = mFullname.text.toString()
            val valid = isValidEmailAndPassInput(mFullNameText, mEmailText, mPasswordText)

            if (valid) {
                Dialog.setTitle("Email Sign Up")
                Dialog.setMessage("SignIn Up and Authenticating User")
                signUpWithEmailAndPassword(mEmailText, mPasswordText)
                Dialog.show()
            } else Toast.makeText(this, "Invalid input Text", Toast.LENGTH_LONG).show()

        }
        mBinding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

            finish()
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
    private fun signUpWithEmailAndPassword(email: String, password: String) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d("TAG_SIGNUP_ACTIVITY", "" + task.result + task.exception)
                    if (task.isSuccessful) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SIGNUP_ACTIVITY", "User signed in Successfully")
                        val user = firebaseAuth.currentUser

                        // send verification mail
                        user!!.sendEmailVerification().addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(this, "User Signed Up Successfully", Toast.LENGTH_LONG).show()
                            }
                        }

                        updateUser(user)
                    }else{
                        // If sign in fails, display a message to the user.
                        Dialog.dismiss()
                        Log.d("SIGNUP_ACTIVITY", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
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

    private fun updateUser(user: FirebaseUser?) {
        val mUser = User(mFullname.text.toString())
        userRef.document(user!!.uid).set(mUser, SetOptions.merge()).addOnCompleteListener {
            if(it.isSuccessful){
                Dialog.dismiss()
                mUser.name.setUserName()
                mUser.isAdmin.setAdmin()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                user.delete()
            }
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
    private fun isValidEmailAndPassInput(
        fullName: String,
        email: String,
        password: String
    ): Boolean {
        if (fullName.isEmpty()) {
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