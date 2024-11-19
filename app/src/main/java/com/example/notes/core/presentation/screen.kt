package com.example.notes.core.presentation

import kotlinx.serialization.Serializable

sealed interface screen {

    @Serializable
    data object NoteList : screen

    @Serializable
    data object AddNote : screen
}