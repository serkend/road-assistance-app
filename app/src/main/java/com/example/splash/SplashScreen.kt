package com.example.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.navigation.FlowNavigator

@Composable
fun SplashScreen(
    flowNavigator: FlowNavigator,
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val isAuthenticated by viewModel.isUserAuthenticatedStateFlow.collectAsState()
    SplashContent()
    LaunchedEffect(isAuthenticated) {
        when (isAuthenticated) {
            true -> flowNavigator.navigateToMainFlow()
            false -> navController.navigate(com.example.app.R.id.action_splashFragment_to_auth_nav_graph)
            null -> {}
        }
    }
}

@Composable
fun SplashContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview(showBackground = true)
fun SplashContentPreview() {
    SplashContent()
}