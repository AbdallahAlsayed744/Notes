package com.example.notes.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.core.domain.usecase.UpsertItem
import com.example.notes.core.presentation.composables.AddNoteScreen
import com.example.notes.core.presentation.util.AddNoteState
import com.example.notes.core.presentation.util.AddNotesActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpsertViewmodel@Inject constructor(
    private val upsertItem: UpsertItem
):ViewModel() {

    private val _Upsertstate = MutableStateFlow(AddNoteState())
    val upsertstate:StateFlow<AddNoteState> get()  = _Upsertstate

    private val _notChannal = Channel<AddNotesActions>(Channel.UNLIMITED)
    val notChannal get()  = _notChannal

    fun onAction() {
        viewModelScope.launch {
            _notChannal.consumeAsFlow().collect {
                when (it) {
                    is AddNotesActions.UpdateTitle -> {
                        _Upsertstate.update {
                            it.copy(title = it.title)
                        }
                    }

                    is AddNotesActions.UpdateDescription -> {
                        _Upsertstate.update {
                            it.copy(description = it.description)
                        }
                    }

                    is AddNotesActions.PickImage -> {
                        _Upsertstate.update {
                            it.copy(imageUrl = it.imageUrl)
                        }
                    }

                    is AddNotesActions.UpdateSearchImageQuery -> {
                        _Upsertstate.update {
                            it.copy(searchImagesQuery = it.searchImagesQuery)
                        }
                    }

                    is AddNotesActions.UpdateImagesDialogVisibility -> {
                        _Upsertstate.update {
                            it.copy(isImagesDialogShowing = !it.isImagesDialogShowing)
                        }
                    }

                    is AddNotesActions.SaveNote -> {
                        viewModelScope.launch {

                            upsertItem.invoke(
                                title = upsertstate.value.title,
                                description = upsertstate.value.description,
                                image = upsertstate.value.imageUrl
                            )





                        }


                    }

                }
            }


        }


    }

    suspend fun upsertNote(
        title: String,
        description: String,
        imageUrl: String
    ): Boolean {
        return upsertItem.invoke(
            title = title,
            description = description,
            image = imageUrl
        )
    }
}