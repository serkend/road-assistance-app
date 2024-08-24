package com.example.auth.presentation.screens.auth.sign_up
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.auth.presentation.screens.auth.sign_up.state.SignUpEvent
import com.example.auth.presentation.screens.auth.sign_up.state.SignUpState
import com.example.core.uikit.R
import com.example.core.uikit.ui.AppTheme
import com.example.navigation.FlowNavigator

@Composable
fun SignUpScreen(
    flowNavigator: FlowNavigator,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        viewModel.onEvent(SignUpEvent.PickPhoto(uri))
    }

    LaunchedEffect(Unit) {
        viewModel.showSnackbar.collect { message ->
            snackbarHostState.showSnackbar(
                message = message ?: context.getString(R.string.unknown_error)
            )
        }
    }

    LaunchedEffect(state.isSignUpSuccessful) {
        if (state.isSignUpSuccessful) {
            flowNavigator.navigateToMainFlow()
        }
    }

    SignUpContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onImageClicked = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        onAction = { viewModel.onEvent(it) }
    )

}

@Composable
fun SignUpContent(
    state: SignUpState,
    snackbarHostState: SnackbarHostState,
    onImageClicked : () -> Unit,
    onAction: (SignUpEvent) -> Unit
) {
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = state.imageUri?.let {
                    rememberAsyncImagePainter(it)
                } ?: painterResource(id = R.drawable.ic_avatar),
                contentDescription = "User Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(124.dp)
                    .clickable { onImageClicked() }
            )

//                Spacer(modifier = Modifier.height(16.dp))

            Column {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onAction(SignUpEvent.EmailChanged(it)) },
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.username,
                    onValueChange = { onAction(SignUpEvent.UsernameChanged(it)) },
                    label = { Text(text = "Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onAction(SignUpEvent.PasswordChanged(it)) },
                    label = { Text(text = "Password") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )

            }

//                Spacer(modifier = Modifier.height(32.dp))

            Box(contentAlignment = Alignment.Center) {
                Button(
                    onClick = { onAction(SignUpEvent.SignUp) },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(text = "Create")
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpScreenPreview() {
    AppTheme {
        SignUpContent(
            state = SignUpState(
                email = "",
                username = "",
                password = "",
                imageUri = null,
                isLoading = false,
                isSignUpSuccessful = false
            ),
            snackbarHostState = SnackbarHostState(), onImageClicked = {},
            onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreenPreview()
}

