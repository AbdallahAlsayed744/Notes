package com.example.notes.core.data.reposatoreis

import com.example.notes.core.data.mapper.tomyimgres
import com.example.notes.core.data.remote.imagesapi.ImagesApi
import com.example.notes.core.domain.model.Images
import com.example.notes.core.domain.repositories.imageslist
import javax.inject.Inject

class imageslistimpl@Inject constructor(
    private val api: ImagesApi

):imageslist {
    override suspend fun getImages(query: String): Images? {
        return api.getImages(query = query)?.tomyimgres()

    }
}