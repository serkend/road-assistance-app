package com.example.roadAssist.presentation.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T> Fragment.bindStateFlow(
    flow: StateFlow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    action: (T) -> Unit
) {
    this.viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            flow.collect {
                action(it)
            }
        }
    }
}

fun <T> Fragment.bindSharedFlow(
    flow: SharedFlow<T>,
    lifecycleState: Lifecycle.State= Lifecycle.State.STARTED,
    action: (T) -> Unit
) {
    this.viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            flow.collect {
                action(it)
            }
        }
    }
}