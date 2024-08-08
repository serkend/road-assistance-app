package com.example.navigation

interface FlowNavigator {
    fun navigateToMainFlow()
    fun navigateToChatsFlow(conversationId: String)
    fun navigateToSplashFlow()
}