package com.example.app

import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.auth.presentation.screens.auth.sign_in.SignInViewModel
import com.example.core.common.ResultState
import com.example.debug.HiltTestActivity
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.IsAuthenticated
import com.example.domain.auth.usecases.auth.SignIn
import com.example.domain.auth.usecases.auth.SignOut
import com.example.domain.auth.usecases.auth.SignUp
import com.example.splash.SplashFragment
import com.example.splash.SplashViewModel
import com.example.ui_test.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SplashFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(HiltTestActivity::class.java)

    private lateinit var authUseCases: AuthUseCases

    @Before
    fun setUp() {
        hiltRule.inject()
        authUseCases = AuthUseCases(
            signIn = mockk(),
            signUp = mockk(),
            signOut = mockk(),
            isAuthenticated = mockk()
        )
    }

    @Test
    fun testSplashFragment_whenUserIsAuthenticated_navigatesToMainFlow() = runBlocking {
        val testViewModel = SplashViewModel(authUseCases)
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        every { authUseCases.isAuthenticated() } returns flowOf(ResultState.Success(true))
        launchFragmentInHiltContainer<SplashFragment> {
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                viewModel = testViewModel
                if (viewLifecycleOwner != null) {
                    navController.setGraph(R.navigation.nav_graph)
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }

        assertTrue(navController.currentDestination?.id == R.id.main_nav_graph)
    }

    @Test
    fun testSplashFragment_whenUserIsNotAuthenticated_navigatesToAuthFlow() = runBlocking {
        val testViewModel = SplashViewModel(authUseCases)
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        every { authUseCases.isAuthenticated() } returns flowOf(ResultState.Success(false))

        launchFragmentInHiltContainer<SplashFragment> {
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                viewModel = testViewModel
                if (viewLifecycleOwner != null) {
                    navController.setGraph(R.navigation.nav_graph)
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }

        assertTrue(navController.currentDestination?.id == com.example.features.auth.presentation.R.id.auth_nav_graph)
    }
}