package com.example.notes.core.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.core.domain.reposatory.FakeNoteRepoImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllItemsTest{


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var fakeNoteRepoImpl: FakeNoteRepoImpl
    lateinit var getAllItems: GetAllItems


    @Before
    fun setup(){
        fakeNoteRepoImpl = FakeNoteRepoImpl()
        getAllItems= GetAllItems(fakeNoteRepoImpl)
    }


    @Test
    fun `should return notes sorted by title`() = runTest{

        val notes = getAllItems.invoke(true)

        for (i in 0..notes.size-2 ){

            assertTrue(notes[i].title<=notes[i+1].title)

        }

    }


    @Test
    fun `should return notes sorted by date added`() = runTest{

        val notes = getAllItems.invoke(false)

        for (i in 0..notes.size-2 ){

            assertTrue(notes[i].dateAdded<=notes[i+1].dateAdded)

        }

    }


}