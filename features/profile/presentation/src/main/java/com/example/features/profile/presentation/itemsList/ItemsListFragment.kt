package com.example.features.profile.presentation.itemsList

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.common.vehicles.VehiclesAdapter
import com.example.core.uikit.extensions.bindFlow
import com.example.core.uikit.extensions.showToast
import com.example.features.profile.presentation.R
import com.example.features.profile.presentation.databinding.FragmentItemsListBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsListFragment : Fragment(R.layout.fragment_items_list) {

    private val binding: FragmentItemsListBinding by viewBinding()
    private val viewModel: ItemsListViewModel by viewModels()

    private val args:ItemsListFragmentArgs by navArgs()

    private var vehiclesAdapter: VehiclesAdapter? = null
    private var jobsAdapter: JobsAdapter? = null

    private var contentType: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentType = args.contentType
        when (contentType) {
            "jobs" -> viewModel.getCurrentUserJobs()
            "requests" -> viewModel.fetchCurrentUserRequests()
            "vehicles" -> viewModel.getVehicles()
            else -> {}
        }
        initViews()
        bindViewModel()
    }

    private fun initViews() = with(binding) {
        setupRecyclerView(args.contentType)
        when (contentType) {
            "jobs" -> {
                textView2.text = getString(com.example.core.uikit.R.string.your_jobs)
                addButton.isVisible = false
            }
            "requests" -> {
                textView2.text = getString(com.example.core.uikit.R.string.your_requests)
                addButton.isVisible = false
            }
            "vehicles" -> {
                textView2.text = getString(com.example.core.uikit.R.string.your_vehicles)
                addButton.isVisible = true
            }
            else -> ""
        }
    }

    private fun bindViewModel() = with(viewModel) {
        bindFlow(showToast) {
            showToast(it)
        }
        bindFlow(jobItemsStateFlow) {
            when(contentType) {
                "jobs" -> jobsAdapter?.submitList(it)
                "requests" -> jobsAdapter?.submitList(it)
                else -> { showToast("Content type is null or empty") }
            }
        }
        bindFlow(vehiclesStateFlow) {
            when(contentType) {
                "vehicles" -> vehiclesAdapter?.submitList(it)
                else -> { showToast("Content type is null or empty") }
            }
        }
    }

    private fun setupRecyclerView(contentType: String?) {
        when (contentType) {
            "jobs" -> setupJobsAdapter()
            "requests" -> setupJobsAdapter()
            "vehicles" -> setupVehiclesAdapter()
        }
    }

    private fun setupJobsAdapter() {
        jobsAdapter = JobsAdapter { _ ->
//            viewModel.onVehicleCardClicked(vehicle.id)
        }
        binding.recyclerView.adapter = jobsAdapter
    }

    private fun setupVehiclesAdapter() {
        vehiclesAdapter = VehiclesAdapter { _ ->
//            viewModel.onVehicleCardClicked(vehicle.id)
        }
        binding.recyclerView.adapter = vehiclesAdapter
    }
}