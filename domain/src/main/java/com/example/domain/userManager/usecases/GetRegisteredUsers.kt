package com.example.domain.userManager.usecases

import com.example.common.Resource
import com.example.domain.common.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRegisteredUsers @Inject constructor(private val userManagerRepository: UserManagerRepository) {
    suspend operator fun invoke() : Flow<Resource<List<User>>> = userManagerRepository.getRegisteredUsersFromServer()
}