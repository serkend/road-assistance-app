package com.example.chat.conversations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat.model.ConversationModel
import com.example.core.common.databinding.ItemConversationBinding
import com.example.features.chats.presentation.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConversationsAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<ConversationModel, ConversationsAdapter.ConversationViewHolder>(
        ConversationDiffCallback()
    ) {

    class ConversationViewHolder(
        private val binding: ItemConversationBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: ConversationModel) {
            binding.textViewName.text = conversation.lastMessage
            binding.textViewTimestamp.text = formatTimestamp(conversation.lastMessageTimestamp ?: 0)
            binding.root.setOnClickListener {
                onClick(conversation.id ?: "")
            }
            Glide.with(binding.userAvatarIv)
                .load(conversation.companionImage)
                .placeholder(com.example.core.common.R.drawable.ic_avatar)
                .error(com.example.core.common.R.drawable.ic_avatar)
                .into(binding.userAvatarIv)
            binding.usernameTextView.text = conversation.companionUsername
        }

        private fun formatTimestamp(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm a", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val binding = ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConversationViewHolder(binding, onClick)
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
