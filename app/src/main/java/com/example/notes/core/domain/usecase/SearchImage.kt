package com.example.notes.core.domain.usecase

import com.example.notes.core.domain.model.Images
import com.example.notes.core.domain.repositories.imageslist
import com.example.notes.core.presentation.util.Resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchImage(
    private val repo:imageslist
) {
    suspend operator fun invoke(query:String): Flow<Resource<Images>> {
        return flow {

            emit(Resource.Loading(true))

            if (query.isEmpty()) {
                emit(Resource.Error())
                emit(Resource.Loading(false))
                return@flow
            }

            val images = try {
               repo.getImages(query)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error())
                emit(Resource.Loading(false))
                return@flow
            }

            images?.let {
                emit(Resource.Success(it))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error())
            emit(Resource.Loading(false))


        }



    }


}