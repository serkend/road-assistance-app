package com.example.domain.files

import android.location.Location
import com.example.core.common.ResultState
import com.example.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadImage @Inject constructor() {
    operator fun invoke(): Flow<ResultState<Location>> = 
}