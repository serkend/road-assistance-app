package com.example.domain.usecases.auth

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class IsAuthenticated @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(viewModelScope : CoroutineScope) : StateFlow<Boolean> {
        return authRepository.isUserAuthenticated(viewModelScope)
    }
}