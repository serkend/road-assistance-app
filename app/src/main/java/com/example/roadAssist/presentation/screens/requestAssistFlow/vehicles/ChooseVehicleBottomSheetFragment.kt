package com.example.roadAssist.presentation.screens.requestAssistFlow.vehicles

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentChooseVehicleBottomSheetBinding
import com.example.roadAssist.databinding.FragmentChooseVehicleTroubleBottomSheetBinding
import com.example.roadAssist.presentation.utils.bindSharedFlow
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView

class ChooseVehicleBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_choose_vehicle_bottom_sheet) {

    private val viewModel: ChooseVehicleBottomSheetViewModel by viewModels()
    private val binding: FragmentChooseVehicleBottomSheetBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.vehicleAddUpdateBottomSheetFragment)
        }
    }
}