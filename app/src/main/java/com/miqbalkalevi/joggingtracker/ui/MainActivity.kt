package com.miqbalkalevi.joggingtracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.miqbalkalevi.joggingtracker.R
import com.miqbalkalevi.joggingtracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        val navController = navHostFragment.navController

        binding.apply {
            bnvMain.setupWithNavController(navController)

            navController
                .addOnDestinationChangedListener { controller, destination, arguments ->
                    when (destination.id) {
                        R.id.runsFragment, R.id.statisticsFragment, R.id.settingsFragment ->
                            bnvMain.isVisible = true
                        else ->
                            bnvMain.isGone = true
                    }
                }
        }


    }
}