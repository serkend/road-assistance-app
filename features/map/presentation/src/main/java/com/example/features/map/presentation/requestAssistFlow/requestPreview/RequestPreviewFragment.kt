package com.example.features.map.presentation.requestAssistFlow.requestPreview

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.uikit.extensions.bindFlow
import com.example.core.uikit.extensions.checkLocationPermission
import com.example.core.uikit.extensions.showToast
import com.example.features.map.presentation.R
import com.example.features.map.presentation.databinding.FragmentRequestPreviewBinding
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
        descriptionTroubleTextView.text = args.troubleName
        confirmButton.setOnClickListener {
            if (requireContext().checkLocationPermission()) {
//                requireContext().showToast("Location Permissions are granted")
                viewModel.saveRequest(
                    trouble = args.troubleName,
                    cost = priceEditText.text.toString()
                )
                Log.e("TAG", "initViews: ${priceEditText.text.toString()}", )
            }
        }
    }

    private fun bindViewModel() = with(viewModel) {
        viewModel.getVehicleById(vehicleId = args.vehicleId)
        bindFlow(fetchedVehicleStateFlow) {
            it?.let {
                binding.makeTextView.text = it.make
                binding.modelTextView.text = it.model
                binding.yearTextView.text = it.year.toString()
            }
        }
        bindFlow(showToast) {
            showToast(it)
        }
    }
}