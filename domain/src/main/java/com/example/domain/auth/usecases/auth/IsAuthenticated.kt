package com.example.domain.auth.usecases.auth

import com.example.domain.auth.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class IsAuthenticated @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(viewModelScope : CoroutineScope) : StateFlow<Boolean> {
        return authRepository.isUserAuthenticated(viewModelScope)
    }
}