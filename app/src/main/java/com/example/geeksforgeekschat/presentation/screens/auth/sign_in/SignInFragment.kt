package com.example.geeksforgeekschat.presentation.screens.auth.sign_in

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.common.Constants.TAG
import com.example.domain.model.SignInCredentials
import com.example.geeksforgeekschat.R
import com.example.geeksforgeekschat.databinding.FragmentSignInBinding
import com.example.geeksforgeekschat.presentation.model.LoginState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var viewBinding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentSignInBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners() {
        viewBinding.signInBtn.setOnClickListener {
            val email = viewBinding.emailET.text.toString()
            val password = viewBinding.passwordET.text.toString()
            viewModel.signIn(SignInCredentials(email, password))
        }
        viewBinding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment2_to_signUpFragment2)
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginSharedFlow.collect { uiState ->
                    when (uiState) {
                        LoginState.Success -> {
                            Log.e(TAG, "Success uiState in signIn ")
                            val navController = findNavController()
                            navController.setGraph(R.navigation.bottom_nav_graph)
                            navController.navigate(
                                R.id.action_global_homeFragment2
                            )
                        }

                        LoginState.Loading -> {

                        }

                        is LoginState.Failure -> {
                            Snackbar.make(
                                viewBinding.root,
                                uiState.error.localizedMessage ?: "Unknown error",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        LoginState.Initial -> {

                        }
                    }
                }
            }
        }
    }

    companion object {

    }
}