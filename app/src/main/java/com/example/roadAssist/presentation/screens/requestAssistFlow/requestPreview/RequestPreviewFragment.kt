package com.example.roadAssist.presentation.screens.requestAssistFlow.requestPreview

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentRequestPreviewBinding
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehicleAddUpdate.VehicleAddUpdateBottomSheetFragmentArgs
import com.example.roadAssist.presentation.utils.bindSharedFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestPreviewFragment : Fragment(R.layout.fragment_request_preview) {

    private val binding: FragmentRequestPreviewBinding by viewBinding()
    private val viewModel: RequestPreviewViewModel by viewModels()
    private val args: RequestPreviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() = with(binding) {
        binding.descriptionTroubleTextView.text = args.troubleName
    }

    private fun bindViewModel() = with(viewModel) {
        viewModel.getVehicleById(vehicleId = args.vehicleId)
        bindSharedFlow(fetchedVehicleStateFlow) {
            binding.makeTextView.text = it.make
            binding.modelTextView.text = it.model
            binding.yearTextView.text = it.year.toString()
        }
    }
}