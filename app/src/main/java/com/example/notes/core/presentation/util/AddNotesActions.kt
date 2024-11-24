package com.example.notes.core.presentation.util

sealed interface AddNotesActions {
    data class UpdateTitle(val newTitle: String) : AddNotesActions
    data class UpdateDescription(val newDescription: String) : AddNotesActions

    data class UpdateSearchImageQuery(val newQuery: String) : AddNotesActions
    data class PickImage(val imageUrl: String) : AddNotesActions

    data object UpdateImagesDialogVisibility : AddNotesActions
    data object SaveNote : AddNotesActions
}