package com.example.data.requests.repository

import com.example.common.ResultState
import com.example.data.requests.dto.RequestDto
import com.example.data.requests.mappers.toDto
import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RequestsRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore, private val mAuth: FirebaseAuth) : RequestsRepository {

    override suspend fun fetchRequests(): Flow<ResultState<List<Request>>> = callbackFlow {
        val uId = mAuth.currentUser?.uid ?: throw RuntimeException("User is null")
        val collectionRef = firestore.collection(RequestDto.FIREBASE_REQUESTS)
        val listener = collectionRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                trySend(ResultState.Failure(e = e.localizedMessage))
                close(e)
                return@addSnapshotListener
            }
            val requests = snapshot?.documents?.mapNotNull { doc ->
                val requestDto = doc.toObject(RequestDto::class.java)
                requestDto?.toDomain()?.copy(id = doc.id, isCurrentUser = (requestDto.userId == uId))
            } ?: emptyList()
            trySend(ResultState.Success(requests))
        }
        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRequestById(requestId: String): Request {
        return firestore.collection(RequestDto.FIREBASE_REQUESTS).document(requestId)
            .get().await()
            .toObject(Request::class.java) ?: throw NoSuchElementException("No request found with ID $requestId")
    }

    override suspend fun saveRequest(request: Request) {
        try {
            val uId = mAuth.currentUser?.uid ?: throw RuntimeException("User is null")
            val requestDto = request.toDto(uId)
            val documentReference = if (requestDto.id.isNullOrEmpty()) {
                firestore.collection(RequestDto.FIREBASE_REQUESTS).document()
            } else {
                firestore.collection(RequestDto.FIREBASE_REQUESTS).document(requestDto.id)
            }
            documentReference.set(requestDto.copy(id = documentReference.id)).await()
        } catch (e: Exception) {
            throw RuntimeException("Failed to save request: ${e.message}", e)
        }
    }

    override suspend fun deleteRequest(request: Request) {
        try {
            firestore.collection(RequestDto.FIREBASE_REQUESTS).document(request.id!!).delete().await()
        } catch (e: Exception) {
            throw RuntimeException("Failed to delete request: ${e.message}", e)
        }
    }

}