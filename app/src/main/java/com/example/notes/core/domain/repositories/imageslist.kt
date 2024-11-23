package com.example.notes.core.domain.repositories

import com.example.notes.core.domain.model.Images

interface imageslist {
    suspend fun getImages(query:String):Images?
}