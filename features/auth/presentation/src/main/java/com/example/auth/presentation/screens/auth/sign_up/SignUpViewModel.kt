package com.example.auth.presentation.screens.auth.sign_up
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.presentation.screens.auth.sign_up.state.SignUpEvent
import com.example.auth.presentation.screens.auth.sign_up.state.SignUpState
import com.example.core.common.Resource
import com.example.domain.auth.model.SignUpCredentials
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    private val _showSnackbar = MutableSharedFlow<Unit>()
    val showSnackbar = _showSnackbar.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> {
                _state.update { it.copy(email = event.email) }
            }
            is SignUpEvent.UsernameChanged -> {
                _state.update { it.copy(username = event.username) }
            }
            is SignUpEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.password) }
            }
            is SignUpEvent.PickPhoto -> {
                _state.update { it.copy(imageUri = event.uri) }
            }
            SignUpEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            val currentState = _state.value
            val user = User(
                email = currentState.email,
                image = currentState.imageUri,
                userName = currentState.username
            )
            authUseCases.signUp(SignUpCredentials(currentState.email, currentState.password, currentState.imageUri), user)
                .collect { result ->
                    when (result) {
                        Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            _state.update { it.copy(isLoading = false, isSignUpSuccessful = true) }
                        }

                        is Resource.Failure -> {
//                            _state.update { it.copy(isLoading = false, snackbarMessage = result.e?.localizedMessage) }
                            _showSnackbar.emit(Unit)
                        }

                        else -> {}
                    }
                }
        }
    }
}



