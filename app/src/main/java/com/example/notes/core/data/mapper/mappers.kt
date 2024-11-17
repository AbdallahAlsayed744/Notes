package com.example.notes.core.data.mapper

import com.example.notes.core.data.local.model
import com.example.notes.core.domain.model.noteitem

fun noteitem.toentitywhenupser():model {
    return model(

        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,

        )
}

 fun noteitem.toentitywhendelete():model{
    return model(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,

    )
 }

fun model.toNoteItem(): noteitem {
    return noteitem(
        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,
        id = id ?: 0
    )
}

