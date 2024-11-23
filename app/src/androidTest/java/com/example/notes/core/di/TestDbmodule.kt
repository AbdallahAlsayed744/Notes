package com.example.notes.core.di

import android.app.Application
import androidx.room.Room
import com.example.notes.core.data.local.Mydatabase
import com.example.notes.core.data.remote.imagesapi.ImagesApi
import com.example.notes.core.domain.repositories.NoteRepo
import com.example.notes.core.domain.usecase.DeleteItem
import com.example.notes.core.domain.usecase.GetAllItems
import com.example.notes.core.domain.usecase.UpsertItem
import com.example.notes.core.reposatory.FakeNoteRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Testdbmodule {

    @Provides
    @Singleton
    fun provideDb(application: Application):Mydatabase{
        return Room.inMemoryDatabaseBuilder(  // Use an in-memory database for testing
            application,
            Mydatabase::class.java,

        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(): NoteRepo {
        return  FakeNoteRepoImpl()
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(
        noteRepository: NoteRepo
    ): GetAllItems {
        return GetAllItems(noteRepository)
    }


    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(
        noteRepository: NoteRepo
    ): DeleteItem {
        return DeleteItem(noteRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertNoteUseCase(
        noteRepository: NoteRepo
    ): UpsertItem {
        return UpsertItem(noteRepository)
    }

    @Provides
    @Singleton
    fun provideimageApi(): ImagesApi {
        return Retrofit.Builder().baseUrl(ImagesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImagesApi::class.java)
    }
}