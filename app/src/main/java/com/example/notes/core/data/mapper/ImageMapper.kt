package com.example.notes.core.data.mapper

import com.example.notes.core.data.remote.dto.ImageListDto
import com.example.notes.core.domain.model.Images


fun ImageListDto.tomyimgres(): Images {
    return Images(
        imageUrl = hits?.map {
            it.previewURL?:""
        } ?: emptyList()
    )
}