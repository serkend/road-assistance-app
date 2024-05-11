package com.example.roadAssist.presentation.screens.chat.conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentConversationsBinding
import com.example.roadAssist.presentation.utils.bindStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationsFragment : Fragment(R.layout.fragment_conversations) {

    private val binding:FragmentConversationsBinding by viewBinding()
    private val viewModel: ConversationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.conversationsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ConversationsAdapter()
        binding.conversationsRecyclerView.adapter = adapter
    }

    private fun observeViewModel() = with(viewModel) {
        bindStateFlow(conversations) { conversations ->
            (binding.conversationsRecyclerView.adapter as ConversationsAdapter).submitList(conversations)
        }
    }
}
