package com.example.notes.core.di

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
object Testdbmodule {

    @Provides
    @Singleton
    fun provideDb(application: Application):Mydatabase{
        return Room.inMemoryDatabaseBuilder(  // Use an in-memory database for testing
            application,
            Mydatabase::class.java,

        ).build()
    }
}