package com.example.domain.auth.repository

import com.example.common.Resource
import com.example.domain.common.User
import kotlinx.coroutines.flow.Flow

interface UserManagerRepository {
    suspend fun getCurrentUserFromServer(): Resource<User>
    suspend fun getRegisteredUsersFromServer(): Flow<Resource<List<User>>>
}