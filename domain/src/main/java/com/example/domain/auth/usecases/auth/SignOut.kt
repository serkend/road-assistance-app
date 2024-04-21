package com.example.domain.auth.usecases.auth

import com.example.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SignOut @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.signOut()
    }
}