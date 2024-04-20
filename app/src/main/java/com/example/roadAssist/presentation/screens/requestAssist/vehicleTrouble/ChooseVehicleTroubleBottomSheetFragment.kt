package com.example.roadAssist.presentation.screens.requestAssist.vehicleTrouble

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentChooseVehicleTroubleBottomSheetBinding
import com.example.roadAssist.presentation.screens.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseVehicleTroubleBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: ChooseVehicleTroubleBottomSheetViewModel by viewModels()
    private val binding: FragmentChooseVehicleTroubleBottomSheetBinding  by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}