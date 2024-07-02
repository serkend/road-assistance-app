package com.example.domain.userManager.usecases

import com.example.domain.userManager.repository.UserManagerRepository
import com.example.domain.common.User
import javax.inject.Inject

class GetUserById @Inject constructor(private val userManagerRepository: UserManagerRepository) {
    suspend operator fun invoke(userId: String): User? = userManagerRepository.getUserById(userId)
}