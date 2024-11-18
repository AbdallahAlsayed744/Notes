package com.example.notes.core.domain.usecase

import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.domain.repositories.NoteRepo

class DeleteItem(
    private val noteRepo: NoteRepo
) {

    operator suspend fun invoke(
        noteitem: noteitem
    ) {
        noteRepo.DeleteNoteById(noteitem)
    }



}