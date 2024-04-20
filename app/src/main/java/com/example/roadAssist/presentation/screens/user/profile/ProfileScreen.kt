package com.example.roadAssist.presentation.screens.user.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.roadAssist.R
import com.example.roadAssist.presentation.components.ProgressBar
import com.example.domain.model.UserModel
import com.example.roadAssist.presentation.utils.AVATAR_SIZE
import com.example.roadAssist.presentation.utils.clickNoRipple

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val context = LocalContext.current
    val profileState = viewModel.profileUiState.collectAsState().value
    if (profileState == null) {
        ProgressBar()
    } else {
        ProfileContent(uiState = profileState, onSignOut = {
            viewModel.signOut()
//            context.startActivity(Intent(context, AuthActivity::class.java))
        })
    }
}

@Composable
fun ProfileContent(uiState: UserModel, onSignOut: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 14.dp, alignment = Alignment.CenterVertically
        )
    ) {
        LoggedProfileImage(imageUri = uiState.image, onImagePicked = {})
        Button(onClick = {
            onSignOut()
        }) {
            Text(text = "Sign Out")
        }
    }
}

@Composable
fun LoggedProfileImage(imageUri: Uri?, onImagePicked: (Uri?) -> Unit) {
    if (imageUri != null) {
        val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { //  onImagePicked(it)
        }
        Image(
            modifier = Modifier.size(AVATAR_SIZE)
                .clip(CircleShape)
                .clickNoRipple { //imagePickerLauncher.launch("image/*")
                },
            painter = rememberAsyncImagePainter(model = imageUri),
            contentDescription = "profile image",
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            modifier = Modifier.size(AVATAR_SIZE)
                .clip(CircleShape)
                .clickNoRipple { //                    imagePickerLauncher.launch("image/*")
                },
            painter = painterResource(R.drawable.ic_avatar),
            contentDescription = "profile image",
        )
    }
}