package com.example.notes.core.data.reposatoreis

import com.example.notes.core.data.local.Mydatabase
import com.example.notes.core.data.mapper.toNoteItem
import com.example.notes.core.data.mapper.toentitywhendelete
import com.example.notes.core.data.mapper.toentitywhenupser
import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.domain.repositories.NoteRepo

class NoteRepoImpl(
    private val mddatabase:Mydatabase
):NoteRepo {

    private val notedao = mddatabase.NoteDao
    override suspend fun UpsertNote(Noteitem: noteitem) {
        notedao.upsertitem(Noteitem.toentitywhenupser())
    }

    override suspend fun DeleteNoteById(Noteitem: noteitem) {
        notedao.deleteitem(Noteitem.toentitywhendelete())
    }

    override suspend fun GetAllNotes(): List<noteitem> {

        return notedao.getAllNotes().map { it.toNoteItem() }

    }
}