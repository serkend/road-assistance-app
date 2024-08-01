package com.example.roadAssist.presentation.screens.auth.sign_in

import com.example.core.common.Resource
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.IsAuthenticated
import com.example.domain.auth.usecases.auth.SignIn
import com.example.domain.auth.usecases.auth.SignOut
import com.example.domain.auth.usecases.auth.SignUp
import com.example.roadAssist.CoroutineTestRule
import com.example.roadAssist.presentation.model.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SignInViewModelTest {

//    @get:Rule
//    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var signIn: SignIn

    @Mock
    private lateinit var authUseCases: AuthUseCases

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        authUseCases = AuthUseCases(
            signIn = signIn,
            signUp = Mockito.mock(SignUp::class.java),
            signOut = Mockito.mock(SignOut::class.java),
            isAuthenticated = Mockito.mock(IsAuthenticated::class.java)
        )
        viewModel = SignInViewModel(authUseCases)
    }

    @Test
    fun `signIn with valid credentials emits Success`() = runTest {
        val credentials = SignInCredentials("test@example.com", "password")
        val successResource = flowOf(Resource.Success(true))

        Mockito.`when`(authUseCases.signIn.invoke(credentials)).thenReturn(successResource)

        viewModel.signIn(credentials)

        val state = viewModel.loginSharedFlow.replayCache.first()
        assertEquals(LoginState.Success, state)
    }

    @Test
    fun `signIn with invalid credentials emits Failure`() = runTest {
        val credentials = SignInCredentials("test@example.com", "wrong_password")
        val exception = Exception("Authentication failed")
        val failureResource = flowOf<Resource<Exception>>(Resource.Failure(exception))

        Mockito.`when`(authUseCases.signIn.invoke(credentials)).thenReturn(failureResource)

        viewModel.signIn(credentials)

        val state = viewModel.loginSharedFlow.replayCache.first()
        assertTrue(state is LoginState.Failure)
        assertEquals(exception, (state as LoginState.Failure).error)
    }
}
