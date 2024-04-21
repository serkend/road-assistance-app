package com.example.roadAssist.presentation.screens.auth.sign_in
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
import com.example.common.Constants.TAG
import com.example.domain.auth.model.SignInCredentials
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentSignInBinding
import com.example.roadAssist.presentation.model.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        LoginState.Success -> {
                            binding.signInProgressBar.isVisible = false
                            binding.signInBtn.setTextColor(currColor)
                            Log.e(TAG, "Success uiState in signIn ")
                            val navController = findNavController()
                            navController.setGraph(R.navigation.bottom_nav_graph)
                            navController.navigate(R.id.action_global_homeFragment2)
                        }

                        LoginState.Loading -> {
                            binding.signInProgressBar.isVisible = true
                            binding.signInBtn.setTextColor(binding.signInBtn.solidColor)
                        }

                        is LoginState.Failure -> {
                            binding.signInProgressBar.isVisible = false
                            binding.signInBtn.setTextColor(currColor)
                            Toast.makeText(
                                binding.root.context,
                                uiState.error.localizedMessage ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        LoginState.Initial -> {
                            binding.signInProgressBar.isVisible = false
                        }
                    }
                }
            }
        }
    }

}