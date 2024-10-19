package com.example.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.uikit.ui.AppTheme
import com.example.navigation.FlowNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val testViewModel: SplashViewModel by viewModels()
    lateinit var viewModel: SplashViewModel

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
                    SplashScreen(
                        flowNavigator = flowNavigator,
                        navController = findNavController(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}