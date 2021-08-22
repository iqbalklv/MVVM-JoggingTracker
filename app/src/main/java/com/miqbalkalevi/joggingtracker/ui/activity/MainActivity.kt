package com.miqbalkalevi.joggingtracker.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.miqbalkalevi.joggingtracker.R
import com.miqbalkalevi.joggingtracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //region Setup nav controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_main) as NavHostFragment
        val navController = navHostFragment.findNavController()
        //endregion

        binding.apply {
            bnvMain.setupWithNavController(navController)

            navController
                .addOnDestinationChangedListener { controller, destination, arguments ->
                    when (destination.id) {
                        R.id.runsFragment, R.id.statisticsFragment, R.id.settingsFragment -> {
                            bnvMain.isVisible = true
                        }
                        else ->
                            bnvMain.isGone = true
                    }
                }
        }
    }
}