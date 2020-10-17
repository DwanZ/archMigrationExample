package com.example.archmigrationexample.view.splash

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.archmigrationexample.R
import com.example.archmigrationexample.view.home.ui.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        pokedexImg.animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        nameImg.animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        openDashboard()
    }

    private fun openDashboard() {
        GlobalScope.launch {
            delay(3000L)
            val intent =
                Intent(
                    this@SplashActivity,
                    HomeActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}