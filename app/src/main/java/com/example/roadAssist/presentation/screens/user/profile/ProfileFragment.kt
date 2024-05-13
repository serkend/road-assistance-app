package com.example.roadAssist.presentation.screens.user.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentProfileBinding
import com.example.roadAssist.presentation.utils.bindStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding: FragmentProfileBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() = with(binding) {
        signOutBtn.setOnClickListener {
            viewModel.signOut()
            val navController = findNavController()
            navController.setGraph(R.navigation.nav_graph)
            navController.navigate(R.id.action_global_splashFragment)
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
                        .placeholder(R.drawable.ic_avatar)
                        .error(R.drawable.ic_avatar)
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