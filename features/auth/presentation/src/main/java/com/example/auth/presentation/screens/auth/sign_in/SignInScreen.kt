package com.example.auth.presentation.screens.auth.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.auth.presentation.screens.auth.sign_in.state.SignInEvent
import com.example.auth.presentation.screens.auth.sign_in.state.SignInState
import com.example.core.uikit.ui.AppTheme
import com.example.features.auth.presentation.R
import com.example.navigation.FlowNavigator

@Composable
fun SignInScreen(
    flowNavigator: FlowNavigator?,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val state by viewModel.signInStateFlow.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    LaunchedEffect(state) {
        if (state.isSignInSuccessful) flowNavigator?.navigateToMainFlow()
        else if (state.showToast != null) {
            snackbarHostState.showSnackbar(
                message = state.showToast ?: context.getString(com.example.core.uikit.R.string.unknown_error)
            )
        }
//        viewModel.showToast.collect {
//            snackbarHostState.showSnackbar(
//                message = state.showToast ?: context.getString(com.example.core.uikit.R.string.unknown_error)
//            )
//        }
    }

    SignInContent(
        snackbarHostState = snackbarHostState,
        signInState = state,
        onAction = { viewModel.onEvent(it) },
        onSignUpClicked = { navController.navigate(R.id.action_signInFragment2_to_signUpFragment2) }
    )
}

@Composable
private fun SignInContent(
    snackbarHostState: SnackbarHostState,
    signInState: SignInState,
    onAction: (SignInEvent) -> Unit,
    onSignUpClicked: () -> Unit
) {
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = signInState.email,
                    onValueChange = { onAction(SignInEvent.EmailChanged(it)) },
                    label = { Text(stringResource(com.example.core.uikit.R.string.email)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = signInState.password,
                    onValueChange = { onAction(SignInEvent.PasswordChanged(it)) },
                    label = { Text(stringResource(com.example.core.uikit.R.string.password)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { onAction(SignInEvent.SignIn) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (signInState.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.height(24.dp).width(24.dp)
                        )
                    } else {
                        Text(stringResource(com.example.core.uikit.R.string.sign_in))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { onSignUpClicked() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(com.example.core.uikit.R.string.sign_up))
                }

            }
        }
    }
}

@Composable
fun SignInScreenPreview() {
    AppTheme {
        SignInContent(
            remember { SnackbarHostState() },
            signInState = SignInState(),
            onAction = { },
            onSignUpClicked = {  }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    SignInScreenPreview()
}
