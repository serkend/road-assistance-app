package com.example.roadAssist.presentation.screens.itemsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roadAssist.R
import com.example.roadAssist.databinding.VehicleCardViewBinding

class JobsAdapter(private val onClick: (JobItem) -> Unit) : ListAdapter<JobItem, JobsAdapter.JobsViewHolder>(VehicleDiffCallback()) {

    class JobsViewHolder(private val binding: VehicleCardViewBinding, val onClick: (JobItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (job: JobItem) = with(binding) {
            statusTextView.isVisible = true

            statusTextView.text = "${job.status.value}"
            textView1.text = "Trouble: ${job.trouble}"
            textView2.text = "Vehicle: ${job.vehicle.make} ${job.vehicle.model} ${job.vehicle.year}"
            textView3.text = "Cost: ${job.cost}"
            itemView.setOnClickListener {
                onClick(job)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val binding = VehicleCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobsViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VehicleDiffCallback : DiffUtil.ItemCallback<JobItem>() {
        override fun areItemsTheSame(oldItem: JobItem, newItem: JobItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JobItem, newItem: JobItem): Boolean {
            return oldItem == newItem
        }
    }

}
