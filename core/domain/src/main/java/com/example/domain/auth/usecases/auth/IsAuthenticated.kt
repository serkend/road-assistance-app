package com.example.domain.auth.usecases.auth

import com.example.core.common.AuthState
import com.example.core.common.Resource
import com.example.core.common.ResultState
import com.example.domain.auth.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class IsAuthenticated @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke() : Flow<ResultState<Boolean>> {
        return authRepository.isUserAuthenticated()
    }
}