package com.example.core.uikit.components.vehicles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.common.vehicles.VehicleModel
import com.example.core.uikit.databinding.VehicleCardViewBinding

class VehiclesAdapter(private val onClick: (VehicleModel) -> Unit) :
    ListAdapter<VehicleModel, VehiclesAdapter.VehicleViewHolder>(VehicleDiffCallback()) {

    inner class VehicleViewHolder(private val binding: VehicleCardViewBinding) :
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
        return VehicleViewHolder(binding)
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
