package com.example.notes.core.domain.repositories

import com.example.notes.core.domain.model.noteitem

interface NoteRepo {

    suspend fun UpsertNote(Noteitem: noteitem)

    suspend fun DeleteNoteById(Noteitem: noteitem)

    suspend fun GetAllNotes(): List<noteitem>


}