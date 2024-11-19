package com.example.notes.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.domain.usecase.DeleteItem
import com.example.notes.core.domain.usecase.GetAllItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllItems: GetAllItems,
    private val deleteItem: DeleteItem
):ViewModel() {

    private val _notestatelist = MutableStateFlow<List<noteitem>>(emptyList())
    val notestatelist:StateFlow<List<noteitem>> = _notestatelist

    private val _orderstate = MutableStateFlow(false)
    val orderstate:StateFlow<Boolean> = _orderstate


    fun getAllnotes(){
        viewModelScope.launch {
            _notestatelist.update {

                getAllItems.invoke(orderstate.value)

            }
        }

    }

    fun deleteItem(noteitem: noteitem){
        viewModelScope.launch {
            deleteItem.invoke(noteitem)
            getAllnotes()
        }

    }

    fun OrderByDateorTime(){
        viewModelScope.launch {
            _orderstate.update {
                !it
            }
            getAllnotes()
        }

    }


}