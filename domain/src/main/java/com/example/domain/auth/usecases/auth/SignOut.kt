package com.example.domain.auth.usecases.auth

import javax.inject.Inject

class SignOut @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.signOut()
    }
}