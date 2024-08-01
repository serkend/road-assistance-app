package com.example.features.profile.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.core.common.extensions.bindStateFlow
import com.example.core.common.navigation.FlowNavigator
import com.example.features.profile.presentation.R
import com.example.features.profile.presentation.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding: FragmentProfileBinding by viewBinding()

    @Inject
    lateinit var navigator: FlowNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() = with(binding) {
        signOutBtn.setOnClickListener {
            viewModel.signOut()
            navigator.navigateToSplashFlow()
        }
        myJobsMaterialCardView.setOnClickListener {
            navigateToList("jobs")
        }
        myRequestsMaterialCardView.setOnClickListener {
            navigateToList("requests")
        }
        myVehiclesMaterialCardView.setOnClickListener {
            navigateToList("vehicles")
        }
    }

    private fun bindViewModel() = with(viewModel) {
        bindStateFlow(profileUiState) { user ->
            if (user != null) {
                binding.avatarIV.let { imageView ->
                    Glide.with(imageView.context)
                        .load(user.image)
                        .placeholder(com.example.core.common.R.drawable.ic_avatar)
                        .error(com.example.core.common.R.drawable.ic_avatar)
                        .into(imageView)
                }
                binding.usernameTextView.text = user.userName
                binding.emailTextView.text = user.email
            }
        }
    }

    private fun navigateToList(contentType: String) {
        val action = ProfileFragmentDirections.actionProfileFragmentToItemsListFragment(contentType)
        findNavController().navigate(action)
    }

}