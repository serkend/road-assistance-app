package com.example.data.fileManager.model

import com.example.domain.filesManager.model.FileDownloadingResult

data class FileDownloadingResultDto (val name: String, val position: Int? = null)

fun FileDownloadingResultDto.toDomain() = FileDownloadingResult(
    name = name,
    position = position
)