package com.android.educo.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.android.educo.R
import com.android.educo.views.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val anim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                if (FirebaseAuth.getInstance().currentUser != null) {
                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashScreen, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onAnimationStart(p0: Animation?) {

            }
        })
       splash_icon.startAnimation(anim)
    }
}