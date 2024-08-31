package com.example.baker_street.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.baker_street.R


class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT = 800
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.baker_street.R.layout.activity_splash)
        val window = window

        // Hide the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        val circle = findViewById<ImageView>(R.id.circle)
        val animation = AnimationUtils.loadAnimation(this, R.anim.circle_animation)

        Handler().postDelayed({
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }, 800)

        circle.startAnimation(animation)

    }
}