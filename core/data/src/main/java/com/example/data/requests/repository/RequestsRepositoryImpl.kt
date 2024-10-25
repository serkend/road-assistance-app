package com.example.data.requests.repository

import com.example.core.common.OrderStatus
import com.example.core.common.ResultState
import com.example.data.requests.dto.OrderDto
import com.example.data.requests.dto.RequestDto
import com.example.data.requests.mappers.toDomain
import com.example.data.requests.mappers.toDto
import com.example.domain.requests.model.Order
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

class RequestsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mAuth: FirebaseAuth
) : RequestsRepository {

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
            .toObject(RequestDto::class.java)?.toDomain() ?: throw NoSuchElementException("No request found with ID $requestId")
    }

    private suspend fun getRequestDtoById(requestId: String): RequestDto {
        return firestore.collection(RequestDto.FIREBASE_REQUESTS)
            .document(requestId)
            .get()
            .await()
            .toObject(RequestDto::class.java)
            ?: throw NoSuchElementException("No request found with ID $requestId")
    }

    override suspend fun saveRequest(request: Request): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading())
        try {
            val uId = mAuth.currentUser?.uid ?: throw RuntimeException("User is null")

            if (checkIfUserAlreadyHasRequest(uId)) {
                emit(ResultState.Failure("User already has an active request"))
            } else {
                val requestDto = request.toDto(uId).copy(userId = uId)
                val documentReference = if (requestDto.id.isNullOrEmpty()) {
                    firestore.collection(RequestDto.FIREBASE_REQUESTS).document()
                } else {
                    firestore.collection(RequestDto.FIREBASE_REQUESTS).document(requestDto.id)
                }

                documentReference.set(requestDto.copy(id = documentReference.id)).await()
                emit(ResultState.Success(Unit))
            }
        } catch (e: Exception) {
            emit(ResultState.Failure("Failed to save request: ${e.message}"))
        }
    }

    private suspend fun checkIfUserAlreadyHasRequest(uId: String): Boolean {
        val existingRequestSnapshot = firestore.collection(RequestDto.FIREBASE_REQUESTS)
            .whereEqualTo("userId", uId)
            .get()
            .await()
        return !existingRequestSnapshot.isEmpty
    }

    override suspend fun deleteRequest(request: Request) {
        try {
            firestore.collection(RequestDto.FIREBASE_REQUESTS).document(request.id!!).delete().await()
        } catch (e: Exception) {
            throw RuntimeException("Failed to delete request: ${e.message}", e)
        }
    }

    override suspend fun acceptRequest(requestId: String): Flow<ResultState<Unit>> = flow {
        val currentUser = mAuth.currentUser ?: throw RuntimeException("User not logged in")

        val request = getRequestDtoById(requestId)
        if (checkIfUserAlreadyAcceptedRequest(currentUser.uid)) {
            emit(ResultState.Failure("You have already accepted order"))
        } else if (checkIfRequestIsAlreadyAccepted(requestId)) {
            emit(ResultState.Failure("The request is accepted by another user"))
        } else {
            val newOrder = Order(
                status = OrderStatus.InProgress,
                executorId = currentUser.uid,
                clientId = request.userId
                    ?: throw RuntimeException("Request doesn't have userId"),
                requestId = requestId
            )

            saveOrder(newOrder)
        }
    }

    override suspend fun saveOrder(order: Order) {
        val orderRef = firestore.collection(OrderDto.FIREBASE_ORDERS).document()
        val orderWithId = order.copy(id = orderRef.id)
        orderRef.set(orderWithId).await()
    }

    private suspend fun checkIfUserAlreadyAcceptedRequest(uId: String): Boolean {
        val existingRequestSnapshot = firestore.collection(OrderDto.FIREBASE_ORDERS)
            .whereEqualTo("executorId", uId)
            .get()
            .await()
        return !existingRequestSnapshot.isEmpty
    }

    private suspend fun checkIfRequestIsAlreadyAccepted(requestId: String): Boolean {
        val acceptedRequest = firestore.collection(OrderDto.FIREBASE_ORDERS)
            .whereEqualTo("requestId", requestId)
            .get()
            .await()
        return !acceptedRequest.isEmpty
    }

    override suspend fun fetchCurrentUserOrder(): Flow<ResultState<Order>> = callbackFlow {
        val currentUser = mAuth.currentUser?.uid ?: throw RuntimeException("User not logged in")

        val query = firestore.collection(OrderDto.FIREBASE_ORDERS)
            .whereEqualTo("executorId", currentUser)
            .limit(1)

        val listener = query.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                val result = ResultState.Failure<Order>(exception.message ?: "Unknown error")
                trySend(result)
                return@addSnapshotListener
            }
            val order = snapshot?.documents?.firstOrNull()?.toObject(OrderDto::class.java)?.toDomain()
            trySend(ResultState.Success(order))

        }
        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchMyAllOrders(): Flow<ResultState<List<Order>>> = callbackFlow {
        val currentUserUid = mAuth.currentUser?.uid ?: throw RuntimeException("User is not logged in")

        val query = firestore.collection(OrderDto.FIREBASE_ORDERS).whereEqualTo("executorId", currentUserUid)

        val listener = query.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(ResultState.Failure(exception.localizedMessage ?: "Unknown Firestore error"))
                close(exception)
                return@addSnapshotListener
            }

            val orders = snapshot?.documents?.mapNotNull { document ->
                document.toObject(OrderDto::class.java)?.toDomain()
            } ?: emptyList()

            trySend(ResultState.Success(orders))
        }

        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchMyAllRequests(): Flow<ResultState<List<Request>>>  = callbackFlow {
        val currentUserUid = mAuth.currentUser?.uid ?: throw RuntimeException("User is not logged in")

        val query = firestore.collection(RequestDto.FIREBASE_REQUESTS)
            .whereEqualTo("userId", currentUserUid)

        val listener = query.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(ResultState.Failure(exception.localizedMessage ?: "Unknown Firestore error"))
                close(exception)
                return@addSnapshotListener
            }

            val requests = snapshot?.documents?.mapNotNull { document ->
                document.toObject(RequestDto::class.java)
                    ?.toDomain()
                    ?.copy(id = document.id, isCurrentUser = true)
            } ?: emptyList()

            trySend(ResultState.Success(requests))
        }

        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)


}