package com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentChooseVehicleBottomSheetBinding
import com.example.roadAssist.presentation.utils.bindSharedFlow
import com.example.roadAssist.presentation.utils.bindStateFlow
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseVehicleBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_choose_vehicle_bottom_sheet) {

    private val viewModel: ChooseVehicleBottomSheetViewModel by viewModels()
    private val binding: FragmentChooseVehicleBottomSheetBinding by viewBinding()
    private lateinit var adapter: VehiclesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.vehicleAddUpdateBottomSheetFragment)
        }
        adapter = VehiclesAdapter { vehicle ->
            viewModel.onCarCardClicked(vehicle.id)
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
    }

}