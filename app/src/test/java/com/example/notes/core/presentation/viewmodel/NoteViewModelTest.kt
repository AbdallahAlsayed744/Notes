package com.example.notes.core.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.MainCoroutineRule
import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.domain.reposatory.FakeNoteRepoImpl
import com.example.notes.core.domain.usecase.DeleteItem
import com.example.notes.core.domain.usecase.GetAllItems
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteViewModelTest{

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: NoteViewModel
    lateinit var getAllItems: GetAllItems
    lateinit var deleteItem: DeleteItem
    lateinit var noteRepo: FakeNoteRepoImpl

    @Before
    fun setup(){
        noteRepo = FakeNoteRepoImpl()
        getAllItems = GetAllItems(noteRepo)
        deleteItem = DeleteItem(noteRepo)
        viewModel = NoteViewModel(getAllItems, deleteItem)
    }


    @Test
    fun `Get empty list , should return empty list`() = runTest {
        noteRepo.shouldHaveFilledList(false)

        viewModel.getAllnotes()

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.notestatelist.value.isEmpty())
    }

    @Test
    fun `Get filled list , should return filled list`()= runTest {

        noteRepo.shouldHaveFilledList(true)

        viewModel.getAllnotes()

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.notestatelist.value.isNotEmpty())
    }

    @Test
    fun `Delete item ,should return lsit without this item`() = runTest {
        noteRepo.shouldHaveFilledList(true)
        viewModel.getAllnotes()


        val note =   noteitem("d title 1", "desc 1", "url1", 1)

        viewModel.deleteItem(note)

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.notestatelist.value.contains(note))


    }

    @Test
    fun `items orderd by date , should return items orderd by date`(){
        noteRepo.shouldHaveFilledList(true)
        viewModel.getAllnotes()

        val notes = viewModel.notestatelist.value

        viewModel.OrderByDateorTime()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        for (i in 0..notes.size-2){
            assertTrue((notes[i].dateAdded)<=notes[i+1].dateAdded)
        }

    }

    @Test
    fun `items orderd by title , should return items orderd by title`(){
        noteRepo.shouldHaveFilledList(true)
        viewModel.getAllnotes()

        val notes = viewModel.notestatelist.value

        viewModel.OrderByDateorTime()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        for (i in 0..notes.size-2){
            assertTrue((notes[i].title)<=notes[i+1].title)
        }

    }

}