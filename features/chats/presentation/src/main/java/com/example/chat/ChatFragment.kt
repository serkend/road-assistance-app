package com.example.chat

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.common.extensions.bindStateFlow
import com.example.features.chats.presentation.R
import com.example.features.chats.presentation.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding: FragmentChatBinding by viewBinding()
    private val viewModel: ChatViewModel by viewModels()
    private val args: ChatFragmentArgs by navArgs()

    private var messagesAdapter: MessagesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initViews()
        bindViewModel()
        viewModel.loadMessages(args.conversationId)
    }

    private fun initViews() = with(binding) {
        buttonSend.setOnClickListener {
            val messageText = binding.editTextMessage.text.toString()
            if (messageText.isNotEmpty()) {
                viewModel.sendMessage(args.conversationId, messageText)
                binding.editTextMessage.text.clear()
            }
        }
    }

    private fun bindViewModel() = with(viewModel) {
        bindStateFlow(messages) {
            messagesAdapter?.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter()
        binding.messageRecyclerView.adapter = messagesAdapter
    }

}