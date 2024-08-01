package com.example.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.app.databinding.FragmentSplashBinding
import com.example.core.common.Constants
import com.example.core.common.navigation.FlowNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var viewBinding: FragmentSplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSplashBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isUserAuthenticated().collect { isAuthenticated ->
                    Log.e(Constants.TAG, "isAuthenticated: $isAuthenticated")
                    if (isAuthenticated) {
                        findNavController().navigate(com.example.app.R.id.action_splashFragment_to_bottom_nav_graph)
                    } else {
                        findNavController().navigate(com.example.app.R.id.action_splashFragment_to_auth_nav_graph)
                    }
                }
            }
        }
    }
}