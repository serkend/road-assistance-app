package com.example.data_test

import com.example.data.chat.repository.ChatRepositoryImpl
import com.example.data.di.DataModule
import com.example.domain.auth.di.AuthDomainModule
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.IsAuthenticated
import com.example.domain.auth.usecases.auth.SignIn
import com.example.domain.auth.usecases.auth.SignOut
import com.example.domain.auth.usecases.auth.SignUp
import com.example.domain.chat.di.ChatDomainModule
import com.example.domain.chat.repository.ChatRepository
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.chat.usecases.GetConversations
import com.example.domain.chat.usecases.GetMessages
import com.example.domain.chat.usecases.GetOrCreateConversation
import com.example.domain.chat.usecases.GetReceiverIdForConversation
import com.example.domain.chat.usecases.SendMessage
import com.example.domain.chat.usecases.SendPhoto
import com.example.domain.requests.repository.RequestsRepository
import com.example.domain.userManager.di.UserManagerDomainModule
import com.example.domain.userManager.repository.UserManagerRepository
import com.example.domain.userManager.usecases.GetUserById
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AuthDomainModule::class, ChatDomainModule::class, UserManagerDomainModule::class]
)
object DomainTestModule {
    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases = AuthUseCases(
        signIn = SignIn(authRepository),
        signOut = SignOut(authRepository),
        signUp = SignUp(authRepository),
        isAuthenticated = IsAuthenticated(authRepository)
    )

    @Provides
    @Singleton
    fun provideChatUseCases(chatRepository: ChatRepository): ChatUseCases = ChatUseCases(
        getConversations = GetConversations(chatRepository),
        getMessages = GetMessages(chatRepository),
        sendMessage = SendMessage(chatRepository),
        getOrCreateConversation = GetOrCreateConversation((chatRepository)),
        getReceiverIdForConversation = GetReceiverIdForConversation(chatRepository),
        sendPhoto = SendPhoto(chatRepository)
    )

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(userManagerRepository: UserManagerRepository): GetUserById =
        GetUserById(userManagerRepository)

}