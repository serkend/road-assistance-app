package com.example.data.maps

import com.example.core.common.ResultState
import com.example.domain.maps.repository.MapsRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(private val googleMapsApi: GoogleMapsApi): MapsRepository {
    override suspend fun getRoute(origin: LatLng, destination: LatLng): Flow<ResultState<List<LatLng>>> = flow {
        emit(ResultState.Loading())
        try {
            val originStr = "${origin.latitude},${origin.longitude}"
            val destinationStr = "${destination.latitude},${destination.longitude}"

            val response = googleMapsApi.getDirections(originStr, destinationStr)
            if (response.isSuccessful) {
                val path = ArrayList<LatLng>()
                response.body()?.routes?.firstOrNull()?.let { route ->
                    route.legs.forEach { leg ->
                        leg.steps.forEach { step ->
                            path.addAll(decodePoly(step.polyline.points))
                        }
                    }
                }
                emit(ResultState.Success(path))
            } else {
                emit(ResultState.Failure("Failed to fetch directions"))
            }
        } catch (e: Exception) {
            emit(ResultState.Failure(e.message ?: "Unknown error"))
        }
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }
}