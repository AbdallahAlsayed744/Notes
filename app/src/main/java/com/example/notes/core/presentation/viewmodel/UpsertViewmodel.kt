package com.example.notes.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.core.domain.usecase.SearchImage
import com.example.notes.core.domain.usecase.UpsertItem
import com.example.notes.core.presentation.composables.AddNoteScreen
import com.example.notes.core.presentation.util.AddNoteState
import com.example.notes.core.presentation.util.AddNotesActions
import com.example.notes.core.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpsertViewmodel@Inject constructor(
    private val upsertItem: UpsertItem,
    private val searchImages: SearchImage

):ViewModel() {

    private val _Upsertstate = MutableStateFlow(AddNoteState())
    val upsertstate:StateFlow<AddNoteState> get()  = _Upsertstate


    private val _noteSavedChannel = Channel<Boolean>()
    val noteSavedFlow = _noteSavedChannel.receiveAsFlow()

    fun onAction(action: AddNotesActions) {
        when (action) {
            is AddNotesActions.UpdateTitle -> {
                _Upsertstate.update {
                    it.copy(title = action.newTitle)
                }
            }

            is AddNotesActions.UpdateDescription -> {
                _Upsertstate.update {
                    it.copy(description = action.newDescription)
                }
            }

            is AddNotesActions.PickImage -> {
                _Upsertstate.update {
                    it.copy(imageUrl = action.imageUrl)
                }

            }

            is AddNotesActions.UpdateSearchImageQuery -> {
                _Upsertstate.update {
                    it.copy(searchImagesQuery = action.newQuery)
                }
                mysearchImages(action.newQuery)
            }

             AddNotesActions.UpdateImagesDialogVisibility -> {
                _Upsertstate.update {
                    it.copy(isImagesDialogShowing = !it.isImagesDialogShowing)
                }
            }

            AddNotesActions.SaveNote -> {
                viewModelScope.launch {
                    val isSaved = upsertNote(
                        title = upsertstate.value.title,
                        description = upsertstate.value.description,
                        imageUrl = upsertstate.value.imageUrl
                    )

                    _noteSavedChannel.send(isSaved)
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

        private var searchJob: Job? = null

        fun mysearchImages(query: String) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(500)

                searchImages.invoke(query)
                    .collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                _Upsertstate.update {
                                    it.copy(imageList = emptyList())
                                }
                            }

                            is Resource.Loading -> {
                                _Upsertstate.update {
                                    it.copy(isLoadingImages = result.isLoading)
                                }
                            }

                            is Resource.Success -> {
                                _Upsertstate.update {
                                    it.copy(
                                        imageList = result.data?.imageUrl ?: emptyList()
                                    )
                                }
                            }
                        }
                    }
            }
        }

    }
