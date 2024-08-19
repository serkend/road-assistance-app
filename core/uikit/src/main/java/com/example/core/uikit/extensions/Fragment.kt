package com.example.core.uikit.extensions

import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


fun Fragment.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, message, duration).show()
}

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