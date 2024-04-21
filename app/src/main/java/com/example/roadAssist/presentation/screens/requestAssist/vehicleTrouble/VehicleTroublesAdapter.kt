package com.example.roadAssist.presentation.screens.requestAssist.vehicleTrouble

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roadAssist.R
import com.example.roadAssist.databinding.VehicleTroubleCardViewBinding

class VehicleTroublesAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<VehicleTroublesAdapter.VehicleTroublesViewHolder>() {

    private var selectedIndex = -1

    inner class VehicleTroublesViewHolder(private val binding: VehicleTroubleCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                notifyItemChanged(selectedIndex)
                selectedIndex = adapterPosition
                notifyItemChanged(selectedIndex)
                onClick(selectedIndex)
            }
        }

        fun bind(vehicleTroubleItem: VehicleTroubleItem) {
            val cardView = binding.root
            if (adapterPosition == selectedIndex) {
                cardView.strokeColor =
                    ContextCompat.getColor(binding.root.context, R.color.colorPrimary)
                cardView.strokeWidth = 4
            } else {
                cardView.strokeColor = Color.TRANSPARENT
                cardView.strokeWidth = 0
            }
            binding.imageView.setImageResource(vehicleTroubleItem.imageResId)
            binding.descriptionTextView.text = vehicleTroubleItem.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleTroublesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VehicleTroubleCardViewBinding.inflate(inflater, parent, false)

        // Use ViewTreeObserver to wait for layout
        parent.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Ensure this listener is only called once
                parent.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Now you can safely access the width
                val recyclerView = parent as RecyclerView
                val width = recyclerView.width - (recyclerView.paddingLeft + recyclerView.paddingRight)
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val cardWidth = width / gridLayoutManager.spanCount

                // Set Layout Parameters
                val layoutParams = binding.root.layoutParams
                layoutParams.width = cardWidth
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.root.layoutParams = layoutParams
            }
        })

        return VehicleTroublesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: VehicleTroublesViewHolder, position: Int) {
        holder.bind(vehicleTroubleList[position])
    }

    override fun getItemCount() = vehicleTroubleList.size

    companion object {
        const val SPAN_COUNT = 3
        private val vehicleTroubleList = listOf(
            VehicleTroubleItem(R.drawable.flat_tire, "Flat Tire Emergency"),
            VehicleTroubleItem(R.drawable.fuel_emergency, "Fuel Emergency"),
            VehicleTroubleItem(R.drawable.battery, "Battery Emergency"),
            VehicleTroubleItem(R.drawable.towing, "Towing Emergency"),
            VehicleTroubleItem(R.drawable.push_car, "Push Start"),
            VehicleTroubleItem(R.drawable.locked, "Locked")
        )
    }
}