package com.miqbalkalevi.joggingtracker.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miqbalkalevi.joggingtracker.databinding.ActivityLandingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
    }
}