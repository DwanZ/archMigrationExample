package com.example.archmigrationexample.view.splash

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.archmigrationexample.R
import com.example.archmigrationexample.databinding.ActivitySplashBinding
import com.example.archmigrationexample.view.home.ui.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pokedexImg.animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        binding.nameImg.animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        openDashboard()
    }

    private fun openDashboard() {
        lifecycleScope.launch {
            delay(3000L)
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            finish()
        }
    }
}
