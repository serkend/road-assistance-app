package com.example.auth.presentation.screens.auth.sign_in

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.core.common.Constants.TAG
import com.example.core.common.AuthState
import com.example.navigation.FlowNavigator
import com.example.domain.auth.model.SignInCredentials
import com.example.features.auth.presentation.R
import com.example.features.auth.presentation.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    lateinit var viewModel: SignInViewModel
    private val injectedViewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var flowNavigator: FlowNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::viewModel.isInitialized) {
            viewModel = injectedViewModel
        }
        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners() {
        binding.signInBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            viewModel.signIn(SignInCredentials(email, password))
        }
        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment2_to_signUpFragment2)
        }
    }

    private fun setObservers() {
        val currColor = binding.signInBtn.currentTextColor

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginSharedFlow.collect { uiState ->
                    when (uiState) {
                        AuthState.Success -> {
                            binding.signInProgressBar.isVisible = false
                            binding.signInBtn.setTextColor(currColor)
                            Log.e(TAG, "Success uiState in signIn ")
                            flowNavigator.navigateToMainFlow()
                        }

                        AuthState.Loading -> {
                            binding.signInProgressBar.isVisible = true
                            binding.signInBtn.setTextColor(binding.signInBtn.solidColor)
                        }

                        is AuthState.Failure -> {
                            binding.signInProgressBar.isVisible = false
                            binding.signInBtn.setTextColor(currColor)
                            Toast.makeText(
                                binding.root.context,
                                uiState.error.localizedMessage ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        AuthState.Initial -> {
                            binding.signInProgressBar.isVisible = false
                        }
                    }
                }
            }
        }
    }

}