package com.example.geeksforgeekschat.presentation.screens.user.registered_users

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.UserModel

@Composable
fun RegisteredUsersScreen(navController: NavController) {
    val viewModel: UsersViewModel = hiltViewModel()
    val usersState = viewModel.usersUiState.collectAsState().value
    RegisteredUsersContent(usersState = usersState)
}

@Composable
fun RegisteredUsersContent(usersState: List<UserModel>?) {
    usersState?.let { users ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(users) { user ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Image(
                        modifier = Modifier.size(94.dp),
                        painter = rememberAsyncImagePainter(user.image.toString()),
                        contentDescription = null
                    )
                    Text(user.email)
                }
            }
        }
    }
}