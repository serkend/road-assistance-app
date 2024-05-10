package com.example.common.extensions

import androidx.fragment.app.Fragment
import android.widget.Toast


fun Fragment.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, message, duration).show()
}