package com.example.domain.usecases.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignOut @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.signOut()
    }
}