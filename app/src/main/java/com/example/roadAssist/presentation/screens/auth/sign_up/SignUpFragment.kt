package com.example.roadAssist.presentation.screens.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentSignUpBinding
import com.example.roadAssist.presentation.utils.bindSharedFlow
import com.example.roadAssist.presentation.utils.bindStateFlow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.pickPhoto(uri)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        bindViewModel()
    }

    private fun bindViewModel() = with(viewModel) {
        bindStateFlow(imageUriFlow) { imageUri ->
            imageUri?.let {
                binding.avatarIV.setImageURI(it)
            }
        }
        bindStateFlow(progressBarState) {
            if (it) {
                binding.createBtn.text = ""
            } else {
                binding.createBtn.text = "Create"
            }
            binding.progressBar.isVisible = it
        }
        bindSharedFlow(snackbarState) {
            Toast.makeText(
                binding.root.context, it ?: "Unknown error", Toast.LENGTH_SHORT
            ).show()
        }
        bindSharedFlow(signUpSharedFlow) {
            if(it) {
                navigateToBottomNavGraph()
            }
        }
    }

    private fun setOnClickListeners() = with(binding) {
        createBtn.setOnClickListener {
            viewModel.signUp(emailET.text.toString(), passwordET.text.toString())
        }
        avatarIV.setOnClickListener {
            pickPhoto()
        }
    }

    private fun pickPhoto() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun navigateToBottomNavGraph() {
        val navController = findNavController()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.bottom_nav_graph, true)
            .setLaunchSingleTop(true)
            .build()

        navController.navigate(R.id.bottom_nav_graph, null, navOptions)
    }
}