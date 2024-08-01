package com.example.features.profile.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Constants.TAG
import com.example.core.common.Resource
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.userManager.usecases.GetCurrentUser
import com.example.domain.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCases: AuthUseCases, private val getCurrentUserUseCase: GetCurrentUser
) : ViewModel() {

    private var _profileUiState: MutableStateFlow<User?> = MutableStateFlow(null)
    val profileUiState = _profileUiState.asStateFlow()

    init {
        getCurrentUser()
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCases.signOut()
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            when (val response = getCurrentUserUseCase()) {
                is Resource.Success -> {
                    _profileUiState.value = response.result
                    Log.e(TAG, "getCurrentUser: $response")
                }
                is Resource.Failure -> {
                    Log.e(TAG, "getCurrentUser: ${response.e}")
                }
                else -> {}
            }
        }
    }
}