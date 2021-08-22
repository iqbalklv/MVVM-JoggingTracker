package com.miqbalkalevi.joggingtracker.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.miqbalkalevi.joggingtracker.databinding.ActivitySplashBinding
import com.miqbalkalevi.joggingtracker.exhaustive
import com.miqbalkalevi.joggingtracker.ui.activity.LandingActivity
import com.miqbalkalevi.joggingtracker.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        lifecycleScope.launchWhenStarted {
            viewModel.splashEvent.collect { event ->
                when (event) {
                    is SplashViewModel.SplashEvent.NavigateToLandingScreen -> {
                        startActivity(Intent(this@SplashActivity, LandingActivity::class.java))
                    }
                    is SplashViewModel.SplashEvent.NavigateToMainScreen -> {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    }
                }.exhaustive
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.onSplashFinished()
        }, 2000)
    }


}