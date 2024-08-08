package com.example.auth.presentation.screens.auth.sign_up

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
import com.example.core.common.extensions.bindSharedFlow
import com.example.core.common.extensions.bindStateFlow
import com.example.navigation.FlowNavigator
import com.example.features.auth.presentation.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    @Inject
    lateinit var flowNavigator: com.example.navigation.FlowNavigator

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
                binding.createBtn.text = getString(com.example.core.common.R.string.create)
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
            viewModel.signUp(emailET.text.toString(), passwordET.text.toString(), usernameET.text.toString())
        }
        avatarIV.setOnClickListener {
            pickPhoto()
        }
    }

    private fun pickPhoto() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun navigateToBottomNavGraph() {
//        val navController = findNavController()
//        val navOptions = NavOptions.Builder()
//            .setPopUpTo(R.id.main_nav_graph, true)
//            .setLaunchSingleTop(true)
//            .build()
//
//        navController.navigate(R.id.main_nav_graph, null, navOptions)

        flowNavigator.navigateToMainFlow()
    }
}