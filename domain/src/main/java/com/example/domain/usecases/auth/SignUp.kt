package com.example.domain.usecases.auth

import com.example.common.Resource
import com.example.domain.model.SignUpCredentials
import com.example.domain.model.UserModel
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUp @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(credentials: SignUpCredentials,userModel: UserModel): Flow<Resource<Any>> {
        return authRepository.signUp(credentials, userModel)
    }
}