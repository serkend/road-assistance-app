package com.example.core.common.navigation

interface FlowNavigator {
    fun navigateToMainFlow()
    fun navigateToChatsFlow(conversationId: String)
    fun navigateToSplashFlow()
}