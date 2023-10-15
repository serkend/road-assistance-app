package com.example.domain.usecases.user_manager

import com.example.common.Resource
import com.example.domain.model.UserModel
import com.example.domain.repository.UserManagerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRegisteredUsers @Inject constructor(private val userManagerRepository: UserManagerRepository) {
    suspend operator fun invoke() : Flow<Resource<List<UserModel>>> = userManagerRepository.getRegisteredUsersFromServer()
}