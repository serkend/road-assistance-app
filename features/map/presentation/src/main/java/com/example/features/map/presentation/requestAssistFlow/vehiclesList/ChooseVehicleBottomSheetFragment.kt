package com.example.features.map.presentation.requestAssistFlow.vehiclesList

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.uikit.components.vehicles.VehiclesAdapter
import com.example.core.uikit.extensions.bindFlow
import com.example.features.map.presentation.R
import com.example.features.map.presentation.databinding.FragmentChooseVehicleBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseVehicleBottomSheetFragment :
    BottomSheetDialogFragment(R.layout.fragment_choose_vehicle_bottom_sheet) {

    private val viewModel: ChooseVehicleBottomSheetViewModel by viewModels()
    private val binding: FragmentChooseVehicleBottomSheetBinding by viewBinding()
    private lateinit var adapter: VehiclesAdapter
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
        adapter = VehiclesAdapter { vehicle ->
            viewModel.onVehicleCardClicked(vehicle.id)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun bindViewModel() = with(viewModel) {
        bindFlow(vehiclesStateFlow) {
            adapter.submitList(it)
        }
        bindFlow(showToastSharedFlow) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        bindFlow(launchRequestPreviewScreenSharedFlow) { vehicleId ->
            val action = ChooseVehicleBottomSheetFragmentDirections.actionChooseVehicleBottomSheetFragmentToRequestPreviewFragment(
                troubleName = args.troubleName, vehicleId = vehicleId
            )
            findNavController().navigate(action)
        }
    }

}