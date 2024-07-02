package com.example.requestAssistFlow.vehiclesList

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auth.R
import com.example.auth.databinding.FragmentChooseVehicleBottomSheetBinding
import com.example.auth.presentation.utils.bindSharedFlow
import com.example.auth.presentation.utils.bindStateFlow
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseVehicleBottomSheetFragment :
    BottomSheetDialogFragment(R.layout.fragment_choose_vehicle_bottom_sheet) {

    private val viewModel: ChooseVehicleBottomSheetViewModel by viewModels()
    private val binding: FragmentChooseVehicleBottomSheetBinding by viewBinding()
    private lateinit var adapter: com.example.common.vehicles.VehiclesAdapter
    private val args: ChooseVehicleBottomSheetFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        binding.addButton.setOnClickListener {
            val action =
                ChooseVehicleBottomSheetFragmentDirections.actionChooseVehicleBottomSheetFragmentToVehicleAddUpdateBottomSheetFragment(
                    args.troubleName
                )
            findNavController().navigate(action)
        }
        adapter = com.example.common.vehicles.VehiclesAdapter { vehicle ->
            viewModel.onVehicleCardClicked(vehicle.id)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun bindViewModel() = with(viewModel) {
        bindStateFlow(vehiclesStateFlow) {
            adapter.submitList(it)
        }
        bindSharedFlow(showToastSharedFlow) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        bindSharedFlow(launchRequestPreviewScreenSharedFlow) { vehicleId ->
            val action =
                ChooseVehicleBottomSheetFragmentDirections.actionChooseVehicleBottomSheetFragmentToRequestPreviewFragment(
                    troubleName = args.troubleName,
                    vehicleId = vehicleId
                )
            findNavController().navigate(action)
        }
    }

}