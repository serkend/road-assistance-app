package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.app.databinding.ActivityMainBinding
import com.example.core.common.navigation.FlowNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FlowNavigator {

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
            if (destination.parent?.id == com.example.features.auth.presentation.R.id.auth_nav_graph) {
                bottomNavBar.visibility = View.GONE
            } else {
                bottomNavBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setupBottomNavigation(navController: NavController) {
        binding.bottomNavBar.setupWithNavController(navController)
    }

    override fun navigateToMainFlow() {
        val navController = findNavController(R.id.fragmentContainerView)
        navController.navigate(R.id.main_nav_graph)
    }

    override fun navigateToChatsFlow(conversationId: String) {
        val navController = findNavController(R.id.fragmentContainerView)
        val action = MainNavGraphDirections.actionGlobalChatsNavGraph(conversationId)
        navController.navigate(action)
    }

    override fun navigateToSplashFlow() {
        val navController = findNavController(R.id.fragmentContainerView)
        val action = NavGraphDirections.actionGlobalSplashFragment()
        navController.navigate(action)
    }
}
