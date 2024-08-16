package com.example.domain.filesManager.repository

import com.example.core.common.ResultState
import com.example.domain.filesManager.model.FileDownloadingResult
import kotlinx.coroutines.flow.Flow

interface FileManagerRepository {
    fun downloadImage(url: String?, imageName: String, position: Int): Flow<ResultState<FileDownloadingResult>>
}