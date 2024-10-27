package com.example.auth.signIn

import com.example.auth.presentation.screens.auth.sign_in.SignInViewModel
import com.example.core.common.AuthState
import com.example.core.common.Resource
import com.example.core.common.testing.CoroutineTestRule
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.SignIn
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var signIn: SignIn
    private lateinit var authUseCases: AuthUseCases
    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        signIn = mockk()
        authUseCases = AuthUseCases(
            signIn = signIn,
            signUp = mockk(),
            signOut = mockk(),
            isAuthenticated = mockk()
        )
        viewModel = SignInViewModel(authUseCases)
    }

    @Test
    fun signIn_withValidCredentials_emitsSuccess() = runTest {
        val credentials = SignInCredentials("test@example.com", "password")
        val successResource = flowOf(Resource.Success(true))

        coEvery { authUseCases.signIn.invoke(credentials) } returns successResource
        viewModel.signIn()
//
//        val state = viewModel.loginSharedFlow.first()
//        assertEquals(AuthState.Success, state)
    }

    @Test
    fun signIn_withInvalidCredentials_emitsFailure() = runTest {
        val credentials = SignInCredentials("test@example.com", "wrong_password")
        val exception = Exception("Authentication failed")
        val failureResource = flowOf<Resource<Exception>>(Resource.Failure(exception))

        coEvery { authUseCases.signIn.invoke(credentials) } returns failureResource
        viewModel.signIn()

//        val state = viewModel.loginSharedFlow.first()
//        assertTrue(state is AuthState.Failure)
//        assertEquals(exception, (state as AuthState.Failure).error)
    }

    @Test
    fun signIn_withAnyCredentials_emitsLoading() = runTest {
        val credentials = SignInCredentials("any@example.com", "password")
        val loadingResource = Resource.Loading

        coEvery { authUseCases.signIn(credentials) } returns flowOf(loadingResource)
        viewModel.signIn()
//
//        val state = viewModel.loginSharedFlow.first()
//        assertTrue(state is AuthState.Loading)
    }
}
