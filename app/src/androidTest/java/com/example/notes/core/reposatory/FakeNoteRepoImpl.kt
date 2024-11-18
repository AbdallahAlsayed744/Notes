package com.example.notes.core.reposatory

import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.domain.repositories.NoteRepo

class FakeNoteRepoImpl: NoteRepo {

    private var noteItems = mutableListOf<noteitem>()


    fun shouldHaveFilledList(shouldHaveFilledList: Boolean) {
        noteItems =
            if (shouldHaveFilledList) {
                mutableListOf(
                    noteitem("d title 1", "desc 1", "url1", 1),
                    noteitem("c title 2", "desc 2", "url2", 2),
                    noteitem("b title 3", "desc 3", "url3", 3),
                    noteitem("a title 4", "desc 4", "url4", 4)
                )
            } else {
                mutableListOf()
            }
    }
    override suspend fun UpsertNote(Noteitem: noteitem) {
        noteItems.add(Noteitem)
    }

    override suspend fun DeleteNoteById(Noteitem: noteitem) {
        noteItems.remove(Noteitem)
    }

    override suspend fun GetAllNotes(): List<noteitem> {

        return noteItems

    }
}