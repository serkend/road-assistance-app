package com.example.geeksforgeekschat.presentation.screens.user.registered_users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Constants.TAG
import com.example.common.Resource
import com.example.domain.model.UserModel
import com.example.domain.usecases.user_manager.GetRegisteredUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getRegisteredUsersUseCase: GetRegisteredUsers,
) : ViewModel() {

    private var _usersUiState: MutableStateFlow<List<UserModel>?> = MutableStateFlow(null)
    val usersUiState = _usersUiState.asStateFlow()

    init {
        getRegisteredUsers()
    }

    private fun getRegisteredUsers() {
        viewModelScope.launch {
            getRegisteredUsersUseCase().collect{response ->
                when (response) {
                    is Resource.Success -> {
                        _usersUiState.value = response.data
                        Log.e(TAG, "getRegisteredUsers: $response")
                    }

                    is Resource.Failure -> {
                        Log.e(TAG, "getRegisteredUsers: ${response.e}")
                    }

                    else -> {}
                }
            }

        }
    }
}