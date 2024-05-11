package com.example.roadAssist.presentation.screens.chat.conversations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.chat.model.Conversation
import com.example.roadAssist.databinding.ItemConversationBinding
import com.example.roadAssist.presentation.screens.chat.model.ConversationModel

class ConversationsAdapter : ListAdapter<ConversationModel, ConversationsAdapter.ConversationViewHolder>(ConversationDiffCallback()) {

    class ConversationViewHolder(private val binding: ItemConversationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: ConversationModel) {
            binding.textViewName.text = conversation.executorId  // Adjust based on your data model
            // Add more bindings here as per your UI design
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val binding = ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConversationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ConversationDiffCallback : DiffUtil.ItemCallback<ConversationModel>() {
        override fun areItemsTheSame(oldItem: ConversationModel, newItem: ConversationModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ConversationModel, newItem: ConversationModel): Boolean {
            return oldItem == newItem
        }
    }
}
