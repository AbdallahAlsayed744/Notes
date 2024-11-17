package com.example.notes.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DB_Dao {

    @Upsert
    suspend fun upsertitem(mymodel: model)

    @Query("SELECT * FROM model")
    suspend fun getAllNotes(): List<model>


    @Delete
    suspend fun deleteitem(mymodel: model)
}