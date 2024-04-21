package com.example.domain.auth.usecases.auth

import com.example.common.Resource
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignIn @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(credentials: SignInCredentials): Flow<Resource<Any>> {
        return authRepository.signIn(credentials)
    }
}