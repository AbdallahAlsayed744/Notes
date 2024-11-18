package com.example.notes.core.domain.usecase

import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.domain.repositories.NoteRepo

class GetAllItems(
    private val noteRepo: NoteRepo
) {

    operator suspend fun invoke(
        isTitle:Boolean
    ):List<noteitem> {
       return if (isTitle){
            noteRepo.GetAllNotes().sortedBy { it.title }


        }
       else{
            noteRepo.GetAllNotes().sortedBy { it.dateAdded }
        }

    }
}