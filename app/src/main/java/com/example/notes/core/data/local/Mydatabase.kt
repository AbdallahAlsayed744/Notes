package com.example.notes.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [model::class], version = 1)
abstract class Mydatabase:RoomDatabase() {

    abstract val NoteDao:DB_Dao

}