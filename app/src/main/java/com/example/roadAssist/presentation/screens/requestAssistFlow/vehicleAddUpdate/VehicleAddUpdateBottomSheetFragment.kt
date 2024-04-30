package com.example.roadAssist.presentation.screens.requestAssistFlow.vehicleAddUpdate

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentVehicleAddUpdateBinding
import com.example.roadAssist.presentation.utils.bindSharedFlow
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleAddUpdateBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_vehicle_add_update) {

    private val binding : FragmentVehicleAddUpdateBinding by viewBinding()
    private val viewModel: VehicleAddUpdateBottomSheetViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() = with(binding) {
        addButton.setOnClickListener {
            if (validateFields()) {
                viewModel.onAddVehicleClicked(
                    make = makeEditText.text.toString(),
                    model = modelEditText.text.toString(),
                    year = yearEditText.text.toString().toInt()
                )
            }
        }
    }

    private fun validateFields() : Boolean = with(binding) {
        if (makeEditText.text.toString().isEmpty() || modelEditText.text.toString()
                .isEmpty() || yearEditText.text.toString().isEmpty()
        ) {
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun bindViewModel() = with(viewModel) {
        bindSharedFlow(showToastMessage) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}