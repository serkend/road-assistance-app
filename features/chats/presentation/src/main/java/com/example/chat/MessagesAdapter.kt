package com.example.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat.model.MessageModel
import com.example.chat.model.MessageViewType
import com.example.core.common.extensions.formatTimestamp
import com.example.features.chats.presentation.databinding.ItemImageMessageBinding
import com.example.features.chats.presentation.databinding.ItemTextMessageBinding

class MessagesAdapter(val onImageClicked: (Url: String?, position: Int, imageName: String) -> Unit) : ListAdapter<MessageModel, RecyclerView.ViewHolder>(
    MessageDiffCallback()
) {

    override fun getItemViewType(position: Int): Int {
        return if (!getItem(position).imageUrl.isNullOrBlank()) MessageViewType.VIEW_TYPE_IMAGE.value else MessageViewType.VIEW_TYPE_TEXT.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MessageViewType.VIEW_TYPE_TEXT.value) {
            val binding = ItemTextMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TextMessageViewHolder(binding)
        } else {
            val binding = ItemImageMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ImageMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is TextMessageViewHolder -> holder.bind(message)
            is ImageMessageViewHolder -> holder.bind(message)
        }
    }

    inner class ImageMessageViewHolder(private val binding: ItemImageMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageModel) {
            binding.textViewTimestamp.text = message.timestamp.formatTimestamp()
            Glide.with(binding.imageViewMessage.context)
                .load(message.imageUrl)
                .into(binding.imageViewMessage)

            binding.root.setOnClickListener {
                onImageClicked(message.imageUrl, adapterPosition, "road_assist_image_${message.timestamp}") // todo %1000
            }

            if (message.isOutgoing) {
                binding.root.gravity = Gravity.END
            } else {
                binding.root.gravity = Gravity.START
            }
        }

        fun showProgressBar(show: Boolean) {
            binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    inner class TextMessageViewHolder(private val binding: ItemTextMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageModel) {
            binding.textViewMessage.text = message.text
            binding.textViewTimestamp.text = message.timestamp.formatTimestamp()
            if (message.isOutgoing) {
                binding.textViewMessage.setBackgroundResource(com.example.core.uikit.R.drawable.background_message_outgoing)
                binding.textViewMessage.setTextColor(ContextCompat.getColor(binding.root.context, com.example.core.uikit.R.color.white))
                binding.root.gravity = Gravity.END
            } else {
                binding.textViewMessage.setBackgroundResource(com.example.core.uikit.R.drawable.background_message_incoming)
                binding.textViewMessage.setTextColor(ContextCompat.getColor(binding.root.context, com.example.core.uikit.R.color.black))
                binding.root.gravity = Gravity.START
            }
        }
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

