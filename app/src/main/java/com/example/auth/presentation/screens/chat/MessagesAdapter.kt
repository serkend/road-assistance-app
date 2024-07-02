package com.example.roadAssist.presentation.screens.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roadAssist.R
import com.example.roadAssist.databinding.ItemMessageBinding
import com.example.roadAssist.presentation.screens.chat.model.MessageModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessagesAdapter : ListAdapter<MessageModel, MessagesAdapter.MessageViewHolder>(MessageDiffCallback()) {

    class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageModel) {
            binding.textViewMessage.text = message.text
            binding.textViewTimestamp.text = formatTimestamp(message.timestamp)
            if (message.isOutgoing) {
                binding.textViewMessage.setBackgroundResource(R.drawable.background_message_outgoing)
                binding.textViewMessage.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                binding.root.gravity = Gravity.END
            } else {
                binding.textViewMessage.setBackgroundResource(R.drawable.background_message_incoming)
                binding.textViewMessage.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                binding.root.gravity = Gravity.START
            }
        }

        private fun formatTimestamp(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm a", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<MessageModel>() {
        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem == newItem
        }
    }
}

