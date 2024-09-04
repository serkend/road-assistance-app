package com.example.chat.conversations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.uikit.extensions.bindFlow
import com.example.features.chats.presentation.R
import com.example.features.chats.presentation.databinding.FragmentConversationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationsFragment : Fragment(R.layout.fragment_conversations) {

    private val binding: FragmentConversationsBinding by viewBinding()
    private val viewModel: ConversationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.conversationsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ConversationsAdapter {
            navigateToChatFragment(it)
        }
        binding.conversationsRecyclerView.adapter = adapter
    }

    private fun observeViewModel() = with(viewModel) {
        bindFlow(conversations) { conversations ->
            (binding.conversationsRecyclerView.adapter as ConversationsAdapter).submitList(conversations)
        }
    }

    private fun navigateToChatFragment(conversationId: String) {
        val action = ConversationsFragmentDirections.actionConversationsFragmentToChatFragment(conversationId)
        findNavController().navigate(action)
    }
}
