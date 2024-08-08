package com.example.features.map.presentation.requestAssistFlow.vehicleTrouble

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ChooseVehicleTroubleBottomSheetViewModel : ViewModel() {
    val clickedCardIdSharedFlow = MutableSharedFlow<Pair<Int,Int>>()
    val launchChooseCarScreenSharedFlow = MutableSharedFlow<String>()
    private var lastSelectedCardId = -1
    private var troubleSelected: String? = null
    fun onCardClicked(currSelectedCardResId: Int, troubleName: String) = viewModelScope.launch {
        clickedCardIdSharedFlow.emit(lastSelectedCardId to currSelectedCardResId)
        lastSelectedCardId = currSelectedCardResId
        troubleSelected = troubleName
    }

    fun onSelectButtonClicked() = viewModelScope.launch {
        troubleSelected?.let {
            launchChooseCarScreenSharedFlow.emit(it)
        }
    }
}