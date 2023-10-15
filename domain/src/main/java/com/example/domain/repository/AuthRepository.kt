package com.example.domain.repository

import com.example.common.Resource
import com.example.domain.model.SignInCredentials
import com.example.domain.model.SignUpCredentials
import com.example.domain.model.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun isUserAuthenticated(viewModelScope: CoroutineScope): StateFlow<Boolean>
    suspend fun signIn(credentials : SignInCredentials): Flow<Resource<Boolean>>
    suspend fun signUp(credentials: SignUpCredentials,userModel: UserModel): Flow<Resource<Boolean>>
    suspend fun signOut(): Resource<Boolean>
}