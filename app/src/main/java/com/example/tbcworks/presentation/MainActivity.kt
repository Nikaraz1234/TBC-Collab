package com.example.tbcworks.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.ui.setupWithNavController
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.enableEdgeToEdge

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Handle edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        // Setup BottomNavigationView with NavController
        binding.bottomNavigationView.setupWithNavController(navController)

        // Top-level destinations
        val topLevelDestinations = setOf(
            R.id.eventHubFragment,
            R.id.browseEventFragment2,
            R.id.myEventsFragment,
            R.id.notificationFragment
        )

        // Show/hide BottomNavigationView based on destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible = destination.id in topLevelDestinations
        }

        // Handle reselection to pop back stack to start destination
        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            val currentDestination: NavDestination? = navController.currentDestination
            when (item.itemId) {
                R.id.eventHubFragment -> {
                    if (currentDestination?.id != R.id.eventHubFragment) {
                        navController.popBackStack(R.id.eventHubFragment, false)
                    }
                }
                R.id.browseEventFragment2 -> {
                    if (currentDestination?.id != R.id.browseEventFragment2) {
                        navController.popBackStack(R.id.browseEventFragment2, false)
                    }
                }
                R.id.myEventsFragment -> {
                    if (currentDestination?.id != R.id.myEventsFragment) {
                        navController.popBackStack(R.id.myEventsFragment, false)
                    }
                }
                R.id.notificationFragment -> {
                    if (currentDestination?.id != R.id.notificationFragment) {
                        navController.popBackStack(R.id.notificationFragment, false)
                    }
                }
            }
        }
    }

    /**
     * Navigate to a BottomNavigationView fragment properly.
     * This handles both Bottom Nav selection and back stack.
     */
    fun navigateToBottomNavFragment(fragmentId: Int) {
        // Only navigate if not already on the destination
        if (navController.currentDestination?.id != fragmentId) {
            val navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true) // Avoid multiple copies
                .setRestoreState(true)
                .setPopUpTo(navController.graph.startDestinationId, false, true) // Reset back stack
                .build()

            navController.navigate(fragmentId, null, navOptions)
        }

        // Update BottomNavigationView selection
        binding.bottomNavigationView.menu.findItem(fragmentId).isChecked = true
    }
}
