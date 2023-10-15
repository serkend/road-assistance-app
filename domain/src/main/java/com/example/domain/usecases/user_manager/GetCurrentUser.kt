package com.example.domain.usecases.user_manager

import com.example.common.Resource
import com.example.domain.model.UserModel
import com.example.domain.repository.UserManagerRepository
import javax.inject.Inject

class GetCurrentUser @Inject constructor(private val userManagerRepository: UserManagerRepository) {
    suspend operator fun invoke() :Resource<UserModel> = userManagerRepository.getCurrentUserFromServer()
}