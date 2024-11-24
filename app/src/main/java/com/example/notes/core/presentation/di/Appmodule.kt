package com.example.notes.core.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.notes.core.data.local.Mydatabase
import com.example.notes.core.data.remote.imagesapi.ImagesApi
import com.example.notes.core.data.reposatoreis.NoteRepoImpl
import com.example.notes.core.data.reposatoreis.imageslistimpl
import com.example.notes.core.domain.repositories.NoteRepo
import com.example.notes.core.domain.repositories.imageslist
import com.example.notes.core.domain.usecase.DeleteItem
import com.example.notes.core.domain.usecase.GetAllItems
import com.example.notes.core.domain.usecase.SearchImage
import com.example.notes.core.domain.usecase.UpsertItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Appmodule {

    @Provides
    @Singleton
    fun provideDb(application: Application): Mydatabase {
        return Room.databaseBuilder(
            application,
            Mydatabase::class.java,
            "Mydatabase.db"

            ).build()
    }


    @Provides
    @Singleton
    fun provideNoteRepo(db: Mydatabase): NoteRepo {
        return NoteRepoImpl(db)
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(repo: NoteRepo): GetAllItems {
        return GetAllItems(repo)
    }

    @Provides
    @Singleton
    fun provideDeleteItemUseCase(repo: NoteRepo): DeleteItem {
        return DeleteItem(repo)
    }

    @Provides
    @Singleton
    fun provideUpsertItemUseCase(repo: NoteRepo): UpsertItem {
        return UpsertItem(repo)
    }

    @Provides
    @Singleton
    fun provideimageApi(): ImagesApi {
        return Retrofit.Builder().baseUrl(ImagesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImagesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(
        imagesApi: ImagesApi
    ): imageslist {
        return imageslistimpl(imagesApi)
    }

    @Provides
    @Singleton
    fun provideSearchImageUseCase(myrepo:imageslist): SearchImage {
        return SearchImage(myrepo)
    }
}