package com.example.auth.presentation.screens.auth.sign_up

import com.example.auth.CoroutineTestRule
import com.example.auth.presentation.screens.auth.sign_in.SignInViewModel
import com.example.core.common.LoginState
import com.example.core.common.Resource
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.SignIn
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SignUpViewModelTest{

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()



    @Before
    fun setUp() {

    }

}
