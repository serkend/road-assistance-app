package com.example.data.auth.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.core.common.Resource
import com.example.domain.auth.model.SignInCredentials
import com.google.common.truth.Truth.assertThat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//@HiltAndroidTest
//class AuthRepositoryImplTest {
//
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    lateinit var authRepository: AuthRepositoryImpl
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//    }
//
//}