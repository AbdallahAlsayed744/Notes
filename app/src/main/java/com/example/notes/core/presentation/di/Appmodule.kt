package com.example.notes.core.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.notes.core.data.local.Mydatabase
import com.example.notes.core.data.reposatoreis.NoteRepoImpl
import com.example.notes.core.domain.repositories.NoteRepo
import com.example.notes.core.domain.usecase.DeleteItem
import com.example.notes.core.domain.usecase.GetAllItems
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}