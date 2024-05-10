package com.example.roadAssist.presentation.screens.requestDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.extensions.showToast
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentRequestDetailsBottomSheetBinding
import com.example.roadAssist.presentation.utils.bindSharedFlow
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RequestDetailsBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_request_details_bottom_sheet) {

    private val binding: FragmentRequestDetailsBottomSheetBinding by viewBinding()
    private val viewModel: RequestDetailsBottomSheetViewModel by viewModels()
    private val args: RequestDetailsBottomSheetFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() = with(binding) {
        val request = args.request
        vehicleTextView.text = request.vehicle.make + " " + request.vehicle.model + " " + request.vehicle.year
        troubleTextView.text = request.trouble
        priceTextView.text = request.cost
        acceptButton.setOnClickListener {
            viewModel.acceptRequest(requestId = request.id ?: throw Exception("In accept request method request id is empty!"))
        }
    }

    private fun bindViewModel() = with(viewModel) {
        bindSharedFlow(requestAcceptedSharedFlow) {
            val navController = findNavController()
            val request = args.request
            val bundle = Bundle().apply {
                putParcelable(ORDER_DESTINATION, LatLng(request.latitude, request.longitude))
            }
            navController.previousBackStackEntry?.savedStateHandle?.set(DRAW_ROUTE_RESULT, bundle)
            findNavController().popBackStack()
        }

        bindSharedFlow(showToast) {
            requireContext().showToast(it)
        }
    }

    companion object {
        const val DRAW_ROUTE_RESULT = "drawRoute"
        const val ORDER_DESTINATION = "destination"
    }
}