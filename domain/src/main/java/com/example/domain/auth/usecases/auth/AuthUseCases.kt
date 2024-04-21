package com.example.domain.auth.usecases.auth

data class AuthUseCases(
    val signIn: SignIn,
    val signUp: SignUp,
    val signOut: SignOut,
    val isAuthenticated: IsAuthenticated
)