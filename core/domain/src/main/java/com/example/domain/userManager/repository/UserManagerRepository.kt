package com.example.domain.userManager.repository

import com.example.core.common.Resource
import com.example.domain.common.User
import kotlinx.coroutines.flow.Flow

interface UserManagerRepository {
    suspend fun getCurrentUserFromServer(): Resource<User>
    suspend fun getRegisteredUsersFromServer(): Flow<Resource<List<User>>>

    suspend fun getUserById(userId: String): User?
}