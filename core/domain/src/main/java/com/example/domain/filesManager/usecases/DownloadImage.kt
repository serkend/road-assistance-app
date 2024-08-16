package com.example.domain.filesManager.usecases

import android.location.Location
import com.example.core.common.ResultState
import com.example.domain.filesManager.model.FileDownloadingResult
import com.example.domain.filesManager.repository.FileManagerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadImage @Inject constructor(private val fileManagerRepository: FileManagerRepository) {
    operator fun invoke(url: String?, imageName: String, position: Int): Flow<ResultState<FileDownloadingResult>> =
        fileManagerRepository.downloadImage(url, imageName, position)

}