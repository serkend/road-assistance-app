package com.example.data_test.repository

import com.example.core.common.Resource
import com.example.domain.common.User
import com.example.domain.userManager.repository.UserManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class FakeUserManagerRepository : UserManagerRepository {

    private var currentUserId: String? = "testUserId"

    private val users = mutableListOf(
        User(
            email = "user1@example.com",
            userName = user1Name,
            id = "user1",
            createdAt = Date(),
            updatedAt = Date(),
            token = "token1"
        ),
        User(
            email = "user2@example.com",
            userName = user2Name,
            id = "user2",
            createdAt = Date(),
            updatedAt = Date(),
            token = "token2"
        )
    )

    override suspend fun getRegisteredUsersFromServer(): Flow<Resource<List<User>>> = flow {
        val loggedUserId = currentUserId
        if (loggedUserId == null) {
            emit(Resource.Failure(Exception("User not logged in")))
            return@flow
        }

        val filteredUsers = users.filter { it.id != loggedUserId }
        emit(Resource.Success(filteredUsers))
    }

    override suspend fun getCurrentUserFromServer(): Resource<User> {
        return try {
            val userId = currentUserId
            userId?.let {
                val user = users.find { it.id == userId }
                user?.let {
                    Resource.Success(it)
                } ?: Resource.Failure(Exception("User not found"))
            } ?: Resource.Failure(Exception("User not logged in"))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getUserById(userId: String): User? {
        return users.find { it.id == userId }
    }

    companion object {
        val user1Name = "user1"
        val user2Name = "user2"
    }

}
