package com.example.auth.sign_up

import app.cash.turbine.test
import com.example.auth.presentation.screens.auth.sign_up.SignUpViewModel
import com.example.auth.presentation.screens.auth.sign_up.state.SignUpEvent
import com.example.core.common.Resource
import com.example.core.common.testing.CoroutineTestRule
import com.example.domain.auth.model.SignUpCredentials
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.SignUp
import com.example.domain.common.User
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var signUp: SignUp
    private lateinit var authUseCases: AuthUseCases
    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setUp() {
        signUp = mockk()
        authUseCases = AuthUseCases(
            signIn = mockk(),
            signUp = signUp,
            signOut = mockk(),
            isAuthenticated = mockk()
        )
        viewModel = SignUpViewModel(authUseCases)
    }

    @Test
    fun `should sign up successfully when valid credentials`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val userName = "Test User"
        val signUpCredentials = SignUpCredentials(email, password, null)
        val user = User(email, null, userName = userName)
        coEvery { authUseCases.signUp(signUpCredentials, user) } returns flowOf(Resource.Loading, Resource.Success(true))

        // Act
        viewModel.onEvent(SignUpEvent.EmailChanged(email))
        viewModel.onEvent(SignUpEvent.PasswordChanged(password))
        viewModel.onEvent(SignUpEvent.UsernameChanged(userName))
        viewModel.onEvent(SignUpEvent.SignUp)

        // Assert
        viewModel.state.test {
            // Initial state
            val initialState = awaitItem()
            assertEquals(false, initialState.isLoading)

            // Loading state
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(true, successState.isSignInSuccessful)
        }
    }

}
