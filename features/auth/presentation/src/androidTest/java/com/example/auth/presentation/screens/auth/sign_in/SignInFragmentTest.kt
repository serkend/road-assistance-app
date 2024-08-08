package com.example.auth.presentation.screens.auth.sign_in

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.data.di.DataModule
import com.example.features.auth.presentation.R
import com.example.navigation.FlowNavigator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import com.example.debug.HiltTestActivity
import com.example.domain.auth.di.AuthDomainModule
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.ui_test.launchFragmentInHiltContainer
import com.google.firebase.FirebaseApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.UninstallModules
import io.mockk.mockk
import io.mockk.verify

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(DataModule::class, AuthDomainModule::class)
class SignInFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var authUseCases: AuthUseCases

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(HiltTestActivity::class.java)

    @Inject
    lateinit var flowNavigator: FlowNavigator

    @Before
    fun init() {
        hiltRule.inject()
        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testSignIn_withValidCredentials_navigatesToMainFlow() {
        val testViewModel = SignInViewModel(authUseCases)
        val navController = mockk<NavController>()

        launchFragmentInHiltContainer<SignInFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.emailET)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordET)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.signInBtn)).perform(click())

        verify { flowNavigator.navigateToMainFlow() }
    }

}
