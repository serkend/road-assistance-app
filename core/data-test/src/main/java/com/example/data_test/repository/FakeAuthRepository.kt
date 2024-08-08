package com.example.data_test.repository

import com.example.core.common.Resource
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.model.SignUpCredentials
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.common.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class FakeAuthRepository : AuthRepository {

    private var isAuthenticated = false

    override fun isUserAuthenticated(viewModelScope: CoroutineScope): StateFlow<Boolean> =
        MutableStateFlow(isAuthenticated)

    override suspend fun signIn(credentials: SignInCredentials): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        if (credentials.email == "test@example.com" && credentials.password == "password") {
            isAuthenticated = true
            emit(Resource.Success(true))
        } else {
            emit(Resource.Failure(Exception("Auth error")))
        }
    }

    override suspend fun signUp(credentials: SignUpCredentials, user: User): Flow<Resource<Boolean>> {
        return flow { emit(Resource.Success(true)) }
    }

    override suspend fun signOut(): Resource<Boolean> {
        isAuthenticated = false
        return Resource.Success(true)
    }
}
