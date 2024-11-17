package com.example.notes.core.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.notes.core.data.local.Mydatabase
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
}