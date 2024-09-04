package com.example.domain.auth.repository

import com.example.core.common.AuthState
import com.example.core.common.Resource
import com.example.core.common.ResultState
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.model.SignUpCredentials
import com.example.domain.common.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun isUserAuthenticated(): Flow<ResultState<Boolean>>
    suspend fun signIn(credentials : SignInCredentials): Flow<Resource<Boolean>>
    suspend fun signUp(credentials: SignUpCredentials,user: User): Flow<Resource<Boolean>>
    suspend fun signOut(): Resource<Boolean>
}