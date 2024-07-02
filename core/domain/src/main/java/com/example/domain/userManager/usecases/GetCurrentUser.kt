package com.example.domain.userManager.usecases

import com.example.common.Resource
import com.example.domain.userManager.repository.UserManagerRepository
import com.example.domain.common.User
import javax.inject.Inject

class GetCurrentUser @Inject constructor(private val userManagerRepository: UserManagerRepository) {
    suspend operator fun invoke(): Resource<User> =
        userManagerRepository.getCurrentUserFromServer()
}