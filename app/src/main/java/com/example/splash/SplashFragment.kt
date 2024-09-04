package com.example.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.app.databinding.FragmentSplashBinding
import com.example.core.common.Constants
import com.example.core.uikit.extensions.bindFlow
import com.example.navigation.FlowNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var viewBinding: FragmentSplashBinding

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
        viewBinding = FragmentSplashBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() = with(viewModel) {
        bindFlow(isUserAuthenticatedStateFlow) { isAuthenticated ->
            when (isAuthenticated) {
                true -> flowNavigator.navigateToMainFlow()
                false -> findNavController().navigate(com.example.app.R.id.action_splashFragment_to_auth_nav_graph)
                null -> {}
            }
            Log.e(Constants.TAG, "isAuthenticated: $isAuthenticated")
        }
    }
}