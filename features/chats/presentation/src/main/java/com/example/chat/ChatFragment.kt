package com.example.chat

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.common.ResultState
import com.example.core.uikit.extensions.bindStateFlow
import com.example.core.uikit.extensions.showToast
import com.example.features.chats.presentation.R
import com.example.features.chats.presentation.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding: FragmentChatBinding by viewBinding()
    private val viewModel: ChatViewModel by viewModels()
    private val args: ChatFragmentArgs by navArgs()

    private var messagesAdapter: MessagesAdapter? = null
    private val selectPhotoLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val selectedImageUri: Uri? = result.data?.data
            if (selectedImageUri != null) {
                uploadPhoto(selectedImageUri)
            }
        }
    }

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
        addButton.setOnClickListener {
            selectPhotoFromGallery()
        }
    }

    private fun bindViewModel() = with(viewModel) {
        bindStateFlow(messages) {
            messagesAdapter?.submitList(it)
        }
        bindStateFlow(downloadState) { state ->
            val holder = state.result?.position?.let {
                binding.messageRecyclerView.findViewHolderForAdapterPosition(it)
            }
            if (holder is MessagesAdapter.ImageMessageViewHolder) {
                when (state) {
                    is ResultState.Failure -> {
                        holder.showProgressBar(false)
                        this@ChatFragment.showToast(state.e ?: "")
                    }

                    is ResultState.Loading -> {
                        holder.showProgressBar(true)
                        this@ChatFragment.showToast("Image is loading...")
                    }

                    is ResultState.Success -> {
                        holder.showProgressBar(false)
                        this@ChatFragment.showToast("${ state.data?.name } was loaded")
                    }
                    ResultState.Initial -> {}
                }
            }
        }
    }

    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter { url, position, name ->
            viewModel.downloadPhoto(url, name, position)
            val holder = binding.messageRecyclerView.findViewHolderForAdapterPosition(position)
            if (holder is MessagesAdapter.ImageMessageViewHolder) {
                holder.showProgressBar(true)
            }
        }
        binding.messageRecyclerView.adapter = messagesAdapter
    }

    private fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        selectPhotoLauncher.launch(intent)
    }

    private fun uploadPhoto(imageUri: Uri) {
        viewModel.sendPhoto(args.conversationId, imageUri)
    }

}