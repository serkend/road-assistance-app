package com.example.roadAssist.presentation.screens.requestAssistFlow.vehicleTrouble

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentChooseVehicleTroubleBottomSheetBinding
import com.example.roadAssist.presentation.utils.bindSharedFlow
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseVehicleTroubleBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_choose_vehicle_trouble_bottom_sheet) {

    private val viewModel: ChooseVehicleTroubleBottomSheetViewModel by viewModels()
    private val binding: FragmentChooseVehicleTroubleBottomSheetBinding  by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() = with(binding) {
        viewModel.onCardClicked(flatTyreCardView.id, flatTyreTV.text.toString())
        flatTyreCardView.setOnClickListener {
            viewModel.onCardClicked(it.id, flatTyreTV.text.toString())
        }
        fuelCardView.setOnClickListener {
            viewModel.onCardClicked(it.id, fuelTV.text.toString())
        }
        batteryCardView.setOnClickListener {
            viewModel.onCardClicked(it.id, batteryTV.text.toString())
        }
        towingCardView.setOnClickListener {
            viewModel.onCardClicked(it.id, towingTV.text.toString())
        }
        pushCardView.setOnClickListener {
            viewModel.onCardClicked(it.id, pushTV.text.toString())
        }
        lockedCarView.setOnClickListener {
            viewModel.onCardClicked(it.id, lockedTV.text.toString())
        }
        selectButton.setOnClickListener {
            viewModel.onSelectButtonClicked()
        }
    }

    private fun bindViewModel() = with(viewModel) {
        bindSharedFlow(launchChooseCarScreenSharedFlow) {
            val action = ChooseVehicleTroubleBottomSheetFragmentDirections.actionChooseVehicleTroubleBottomSheetFragmentToChooseVehicleBottomSheetFragment(it)
            findNavController().navigate(action)
        }
        bindSharedFlow(clickedCardIdSharedFlow) { (lastResId, currResId) ->
            when(lastResId) {
                binding.flatTyreCardView.id -> {
                    unpressCardView(binding.flatTyreCardView)
                }
                binding.fuelCardView.id -> {
                    unpressCardView(binding.fuelCardView)
                }
                binding.batteryCardView.id -> {
                    unpressCardView(binding.batteryCardView)
                }
                binding.towingCardView.id -> {
                    unpressCardView(binding.towingCardView)
                }
                binding.pushCardView.id -> {
                    unpressCardView(binding.pushCardView)
                }
                binding.lockedCarView.id -> {
                    unpressCardView(binding.lockedCarView)
                }

            }
            when(currResId) {
                binding.flatTyreCardView.id -> {
                    pressCardView(binding.flatTyreCardView)
                }
                binding.fuelCardView.id -> {
                    pressCardView(binding.fuelCardView)
                }
                binding.batteryCardView.id -> {
                    pressCardView(binding.batteryCardView)
                }
                binding.towingCardView.id -> {
                    pressCardView(binding.towingCardView)
                }
                binding.pushCardView.id -> {
                    pressCardView(binding.pushCardView)
                }
                binding.lockedCarView.id -> {
                    pressCardView(binding.lockedCarView)
                }
            }
        }
    }

    private fun unpressCardView(materialCardView: MaterialCardView) {
        materialCardView.strokeColor = Color.TRANSPARENT
        materialCardView.strokeWidth = 0
    }
    private fun pressCardView(materialCardView: MaterialCardView) {
        materialCardView.strokeColor = ContextCompat.getColor(binding.root.context, R.color.pink2)
        materialCardView.strokeWidth = 4
    }
}