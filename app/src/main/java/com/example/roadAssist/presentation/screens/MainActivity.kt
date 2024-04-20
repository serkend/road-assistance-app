package com.example.roadAssist.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.roadAssist.R
import com.example.roadAssist.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
            .apply { setContentView(this.root) }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        setBottomNavigationListener(navController)
        setupBottomNavigation(navController)
    }

    private fun setBottomNavigationListener(navController: NavController) = with(binding) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.parent?.id == R.id.auth_nav_graph) {
                bottomNavBar.visibility = View.GONE
            } else {
                bottomNavBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setupBottomNavigation(navController: NavController) {
        binding.bottomNavBar.setupWithNavController(navController)
    }
}
