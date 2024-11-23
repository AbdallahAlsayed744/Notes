package com.example.notes.core.data.remote.imagesapi

import com.example.notes.core.data.remote.dto.ImageListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("/api/")
    suspend fun getImages(
        @Query("key") key:String=ImagesApi.key,
        @Query("q") query:String,
    ):ImageListDto?


    companion object{
        const val BASE_URL = "https://pixabay.com"
        const val key="47241534-af17aef94520432f4faf1ef5b"
    }
}