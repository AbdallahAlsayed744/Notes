package com.example.notes.core.domain.usecase

import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.domain.repositories.NoteRepo

class UpsertItem(
    private val noteRepository: NoteRepo
) {
    suspend operator fun invoke(
        title:String,
        description:String,
        image:String,
    ):Boolean{

        if (title.isEmpty()||description.isEmpty()){
            return false
        }
        else{
           val note = noteitem(
                title = title,
                description = description,
                imageUrl = image,
               dateAdded = System.currentTimeMillis()

           )
            noteRepository.UpsertNote(note)
            return true

        }


    }
}