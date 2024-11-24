package com.example.notes.core.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.MainCoroutineRule
import com.example.notes.core.domain.reposatory.FakeImageRepo
import com.example.notes.core.domain.reposatory.FakeNoteRepoImpl
import com.example.notes.core.domain.usecase.SearchImage
import com.example.notes.core.domain.usecase.UpsertItem
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertViewmodelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()



    lateinit var viewModel: UpsertViewmodel
    lateinit var upsertItem: UpsertItem
    lateinit var searchImage: SearchImage
    private lateinit var fakeNotesRepositoryImpl: FakeNoteRepoImpl
    lateinit var fakeimageimpl: FakeImageRepo

    @Before
    fun setup(){
        fakeNotesRepositoryImpl = FakeNoteRepoImpl()
        fakeimageimpl = FakeImageRepo()
        upsertItem = UpsertItem(fakeNotesRepositoryImpl)
        searchImage = SearchImage(fakeimageimpl)
        viewModel = UpsertViewmodel(upsertItem,searchImage)
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

    @Test
    fun `search image with empty query, image list is empty`() = runTest {
        viewModel.searchImages("")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        Truth.assertThat(
            viewModel.upsertstate.value.imageList.isEmpty()
        ).isTrue()
    }

    @Test
    fun `search image with a valid query but with error, image list is empty`() = runTest {
        fakeimageimpl.shouldReturnError=true

        viewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        Truth.assertThat(
            viewModel.upsertstate.value.imageList.isEmpty()
        ).isTrue()
    }

    @Test
    fun `search image with a valid query, image list is not empty`() = runTest {

        viewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        Truth.assertThat(
            viewModel.upsertstate.value.imageList.isNotEmpty()
        ).isTrue()
    }

}