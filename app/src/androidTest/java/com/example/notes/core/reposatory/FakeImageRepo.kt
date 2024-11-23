package com.example.notes.core.reposatory

import com.example.notes.core.domain.model.Images

class FakeImageRepo {


    var shouldReturnError = false

    fun Shouldfechdata(value:Boolean){
         shouldReturnError = value
    }

    fun getImages(query: String): Images? {
        return if (shouldReturnError) {
            null
        } else {
           Images(
                imageUrl = listOf("images1","images2","images3")
            )
        }

    }

}