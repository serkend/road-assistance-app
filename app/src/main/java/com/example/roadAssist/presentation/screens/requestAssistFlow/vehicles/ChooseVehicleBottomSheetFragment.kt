package com.example.roadAssist.presentation.screens.requestAssistFlow.vehicles

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentChooseVehicleTroubleBottomSheetBinding
import com.example.roadAssist.presentation.utils.bindSharedFlow
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView

class ChooseVehicleBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_choose_vehicle_bottom_sheet) {

    private val viewModel: ChooseVehicleBottomSheetViewModel by viewModels()
    private val binding: FragmentChooseVehicleTroubleBottomSheetBinding  by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
        viewModel.onCardClicked(binding.flatTyreCardView.id)
    }

    private fun initViews() = with(binding) {
        flatTyreCardView.setOnClickListener {
            viewModel.onCardClicked(it.id)
        }
        fuelCardView.setOnClickListener {
            viewModel.onCardClicked(it.id)
        }
        batteryCardView.setOnClickListener {
            viewModel.onCardClicked(it.id)
        }
        towingCardView.setOnClickListener {
            viewModel.onCardClicked(it.id)
        }
        pushCardView.setOnClickListener {
            viewModel.onCardClicked(it.id)
        }
        lockedCarView.setOnClickListener {
            viewModel.onCardClicked(it.id)
        }
    }

    private fun bindViewModel() = with(viewModel) {
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