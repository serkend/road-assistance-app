package com.example.auth.presentation.screens.auth.sign_in

import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.uikit.ui.AppTheme
import com.example.navigation.FlowNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val testViewModel: SignInViewModel by viewModels()
    lateinit var viewModel: SignInViewModel

    @Inject
    lateinit var flowNavigator: FlowNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        if (!::viewModel.isInitialized) {
            viewModel = testViewModel
        }
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    SignInScreen(
                        flowNavigator = flowNavigator,
                        navController = findNavController(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
