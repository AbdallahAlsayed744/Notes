package com.example.notes.core.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.domain.reposatory.FakeNoteRepoImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteItemTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var deleteItem: DeleteItem
    lateinit var fakeNoteRepoImpl: FakeNoteRepoImpl


    @Before
    fun setup(){
        fakeNoteRepoImpl = FakeNoteRepoImpl()
        deleteItem = DeleteItem(fakeNoteRepoImpl)
        fakeNoteRepoImpl.shouldHaveFilledList(true)
    }

    @Test
    fun `delete item() , should delete item `() = runTest {

//        fakeNoteRepoImpl.shouldHaveFilledList(true)


        val note =  noteitem("d title 1", "desc 1", "url1", 1)


        deleteItem.invoke(note)

       assertFalse(fakeNoteRepoImpl.GetAllNotes().contains(note))





    }



}