package com.example.geeksforgeekschat.presentation.screens.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Snackbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.geeksforgeekschat.R
import com.example.geeksforgeekschat.databinding.FragmentSignUpBinding
import com.example.geeksforgeekschat.presentation.utils.bindSharedFlow
import com.example.geeksforgeekschat.presentation.utils.bindStateFlow
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        bindViews()
        bindSharedFlow(viewModel.signUpSharedFlow) {
            if(it) {
                findNavController().popBackStack(R.id.bottom_nav_graph, true)
            }
        }
    }

    private fun bindViews() = with(viewModel) {
        bindStateFlow(imageUriFlow) {
            if (it != null) {
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
            Snackbar.make(binding.root, it ?: "Unknown error", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun setOnClickListeners() {
        binding.createBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            viewModel.signUp(email = email, password = password)
        }
        binding.avatarIV.setOnClickListener {
            pickPhoto()
        }
    }

    private fun pickPhoto() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    companion object {

    }
}