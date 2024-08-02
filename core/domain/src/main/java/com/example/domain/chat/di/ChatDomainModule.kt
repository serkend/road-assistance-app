package com.example.domain.chat.di

import com.example.domain.chat.repository.ChatRepository
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.chat.usecases.GetConversations
import com.example.domain.chat.usecases.GetMessages
import com.example.domain.chat.usecases.GetOrCreateConversation
import com.example.domain.chat.usecases.GetReceiverIdForConversation
import com.example.domain.chat.usecases.SendMessage
import com.example.domain.requests.repository.RequestsRepository
import com.example.domain.requests.usecases.requests.AcceptRequest
import com.example.domain.requests.usecases.requests.DeleteRequest
import com.example.domain.requests.usecases.requests.FetchMyAllRequests
import com.example.domain.requests.usecases.requests.FetchRequests
import com.example.domain.requests.usecases.requests.GetRequestById
import com.example.domain.requests.usecases.requests.RequestsUseCases
import com.example.domain.requests.usecases.requests.SaveRequest
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatDomainModule {



}