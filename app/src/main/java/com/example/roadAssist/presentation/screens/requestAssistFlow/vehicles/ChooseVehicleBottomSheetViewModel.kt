package com.example.roadAssist.presentation.screens.requestAssistFlow.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ChooseVehicleBottomSheetViewModel : ViewModel() {
    val clickedCardIdSharedFlow = MutableSharedFlow<Pair<Int,Int>>()
    private var lastSelectedCardId = -1
    fun onCardClicked(currSelectedCardResId: Int) = viewModelScope.launch {
        clickedCardIdSharedFlow.emit(lastSelectedCardId to currSelectedCardResId)
        lastSelectedCardId = currSelectedCardResId
    }
}