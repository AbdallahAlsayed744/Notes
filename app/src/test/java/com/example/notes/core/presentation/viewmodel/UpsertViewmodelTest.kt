package com.example.notes.core.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.core.domain.reposatory.FakeNoteRepoImpl
import com.example.notes.core.domain.usecase.UpsertItem
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertViewmodelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()



    lateinit var viewModel: UpsertViewmodel
    lateinit var upsertItem: UpsertItem
    private lateinit var fakeNotesRepositoryImpl: FakeNoteRepoImpl

    @Before
    fun setup(){
        fakeNotesRepositoryImpl = FakeNoteRepoImpl()
        upsertItem = UpsertItem(fakeNotesRepositoryImpl)
        viewModel = UpsertViewmodel(upsertItem)
    }

    @Test
    fun `upsert item with empty title, should return false`() = runTest {
        val result =viewModel.upsertNote(
            title = "",
            description = "description",
            imageUrl = "image"
        )

        assertFalse(result)
    }

    @Test
    fun `upsert item with empty description , should return false`() = runTest {
        val result = viewModel.upsertNote(
            title = "title",
            description = "",
            imageUrl = "image"
        )

        assertFalse(result)
    }


    @Test
    fun `upsert item with valid title and description, should return true`() = runTest {
        val result = viewModel.upsertNote(
            title = "title",
            description = "description",
            imageUrl = "image"
        )

        assertTrue(result)
    }

}