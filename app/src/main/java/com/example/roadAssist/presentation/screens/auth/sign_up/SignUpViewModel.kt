package com.example.roadAssist.presentation.screens.auth.sign_up

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.common.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authUseCases: AuthUseCases) :
    ViewModel() {
    private var _signUpSharedFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val signUpSharedFlow = _signUpSharedFlow.asSharedFlow()

    private var _imageUriFlow: MutableStateFlow<Uri?> = MutableStateFlow(null)
    val imageUriFlow = _imageUriFlow.asStateFlow()

    private var _progressBarState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val progressBarState = _progressBarState.asStateFlow()

    private var _snackbarState: MutableSharedFlow<String?> = MutableSharedFlow()
    var snackbarState = _snackbarState.asSharedFlow()

    fun signUp(email: String ,password: String) {
        viewModelScope.launch {
            val imageUri = imageUriFlow.value
            val user = User(
                email = email,
                image = imageUri
            )
            authUseCases.signUp(SignUpCredentials(email, password, imageUri), user)
                .collect { state ->
                    when (state) {
                        Resource.Loading -> {
                            _progressBarState.update { true }
                        }

                        is Resource.Success -> {
                            _signUpSharedFlow.emit(true)
                        }

                        is Resource.Failure -> {
                            _progressBarState.update { false }
                            _snackbarState.emit(state.e?.localizedMessage)
                        }

                        else -> {}
                    }
                }
        }
    }

    fun pickPhoto(imageUri: Uri?) {
        _imageUriFlow.update { imageUri }
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCases.signOut()
        }
    }

}