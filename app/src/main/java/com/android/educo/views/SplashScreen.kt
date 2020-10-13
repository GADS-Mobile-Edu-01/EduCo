package com.android.educo.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.android.educo.R
import com.android.educo.views.auth.WelcomeActivity
import com.android.educo.views.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val user = FirebaseAuth.getInstance().currentUser

        Handler(mainLooper).postDelayed({
            val intent = Intent(
                this,
                if (user != null)
                    MainActivity::class.java
                else
                    WelcomeActivity::class.java
            )
            startActivity(intent)

            finish()
        }, 3000)
    }
}