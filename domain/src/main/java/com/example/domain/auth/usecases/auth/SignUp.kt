package com.example.domain.auth.usecases.auth

import com.example.common.Resource
import com.example.domain.auth.model.SignUpCredentials
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.common.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUp @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(credentials: SignUpCredentials,user: User): Flow<Resource<Any>> {
        return authRepository.signUp(credentials, user)
    }
}