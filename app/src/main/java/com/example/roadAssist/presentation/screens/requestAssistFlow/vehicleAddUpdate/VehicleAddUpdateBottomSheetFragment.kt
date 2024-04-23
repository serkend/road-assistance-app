package com.example.roadAssist.presentation.screens.requestAssistFlow.vehicleAddUpdate

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentVehicleAddUpdateBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class VehicleAddUpdateBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_vehicle_add_update) {

    private val binding : FragmentVehicleAddUpdateBinding by viewBinding()
    private val viewModel: VehicleAddUpdateBottomSheetViewModel by viewModels()

}