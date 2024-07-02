package com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roadAssist.R
import com.example.roadAssist.databinding.VehicleCardViewBinding
import com.example.roadAssist.databinding.VehicleTroubleCardViewBinding
class VehiclesAdapter(private val onClick: (VehicleModel) -> Unit) : ListAdapter<VehicleModel, VehiclesAdapter.VehicleViewHolder>(VehicleDiffCallback()) {

    class VehicleViewHolder(private val binding: VehicleCardViewBinding, val onClick: (VehicleModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicle: VehicleModel) {
            binding.textView1.text = "Make: ${vehicle.make}"
            binding.textView2.text = "Model: ${vehicle.model}"
            binding.textView3.text = "Year: ${vehicle.year}"
            itemView.setOnClickListener {
                onClick(vehicle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding = VehicleCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VehicleDiffCallback : DiffUtil.ItemCallback<VehicleModel>() {
        override fun areItemsTheSame(oldItem: VehicleModel, newItem: VehicleModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VehicleModel, newItem: VehicleModel): Boolean {
            return oldItem == newItem
        }
    }
}
