package com.android.educo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.android.educo.views.auth.WelcomeActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent (this@SplashScreen, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}