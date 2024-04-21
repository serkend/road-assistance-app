package com.example.domain.auth.repository

import com.example.common.Resource
import com.example.domain.common.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun isUserAuthenticated(viewModelScope: CoroutineScope): StateFlow<Boolean>
    suspend fun signIn(credentials : SignInCredentials): Flow<Resource<Boolean>>
    suspend fun signUp(credentials: SignUpCredentials,user: User): Flow<Resource<Boolean>>
    suspend fun signOut(): Resource<Boolean>
}