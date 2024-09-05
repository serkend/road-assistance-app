package com.example.app.splash

import app.cash.turbine.test
import com.example.chat.conversations.ConversationsViewModel
import com.example.core.common.AuthState
import com.example.core.common.ResultState
import com.example.core.common.testing.CoroutineTestRule
import com.example.data_test.repository.FakeAuthRepository
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.IsAuthenticated
import com.example.domain.auth.usecases.auth.SignIn
import com.example.domain.auth.usecases.auth.SignOut
import com.example.domain.auth.usecases.auth.SignUp
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.userManager.usecases.GetUserById
import com.example.splash.SplashViewModel
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var authUseCases: AuthUseCases
    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        authUseCases = AuthUseCases(
            signIn = mockk(),
            signUp = mockk(),
            signOut = mockk(),
            isAuthenticated = mockk()
        )
    }

    @Test
    fun isUserAuthenticated_initialValue_emitsNull() = runTest {
        every { authUseCases.isAuthenticated.invoke() } returns flow {
            emit(ResultState.Success(true))
        }
        viewModel = SplashViewModel(authUseCases)

        viewModel.isUserAuthenticatedStateFlow.test {
            val initial = awaitItem()
            Truth.assertThat(initial).isNull()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun isUserAuthenticated_whenAuthenticated_emitsSuccessTrue() = runTest {
        every { authUseCases.isAuthenticated.invoke() } returns flow {
            emit(ResultState.Success(true))
        }
        viewModel = SplashViewModel(authUseCases)

        viewModel.isUserAuthenticatedStateFlow.test {
            awaitItem()
            val actual = awaitItem()
            Truth.assertThat(actual).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }
}