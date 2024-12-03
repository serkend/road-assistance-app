package com.example.data.auth.repository

import androidx.test.platform.app.InstrumentationRegistry
import com.example.core.common.Resource
import com.example.core.common.testing.CoroutineTestRule
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.model.SignUpCredentials
import com.example.domain.common.User
import com.example.domain.storage.repository.StorageRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthRepositoryImplTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var authRepository: AuthRepositoryImpl
    private val firebaseAuth: FirebaseAuth = mockk(relaxed = true)
    private val firestore: FirebaseFirestore = mockk(relaxed = true)
    private val storage: StorageRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        authRepository = AuthRepositoryImpl(firebaseAuth, firestore, storage)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun signIn_credentialsAreCorrect_returnsSuccess() = runTest {
        val credentials = SignInCredentials("test@example.com", "password")
        coEvery { authRepository.signIn(credentials) } returns flowOf(Resource.Success(true))

        val flow = authRepository.signIn(credentials)

        assertEquals(flow.first(), Resource.Success(true))
    }

    @Test
    fun signIn_invalidCredentials_returnsFailure() = runTest {
        val credentials = SignInCredentials("test@example.com", "wrong_password")
        val exception = FirebaseAuthException("ERROR_WRONG_PASSWORD", "The password is invalid")

        coEvery { firebaseAuth.signInWithEmailAndPassword(credentials.email, credentials.password).await() } throws exception

        val flow = authRepository.signIn(credentials)

//        assertThat(flow.first()).isEqualTo(Resource.Failure(exception))
        assertEquals(flow.first(), Resource.Failure<Exception>(exception))
    }
}

