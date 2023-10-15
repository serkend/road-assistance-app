package com.example.domain.repository

import com.example.common.Resource
import com.example.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserManagerRepository {
    suspend fun getCurrentUserFromServer(): Resource<UserModel>
    suspend fun getRegisteredUsersFromServer(): Flow<Resource<List<UserModel>>>
}