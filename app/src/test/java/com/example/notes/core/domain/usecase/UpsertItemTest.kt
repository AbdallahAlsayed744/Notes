package com.example.notes.core.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.MainCoroutineRule
import com.example.notes.core.domain.reposatory.FakeNoteRepoImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertItemTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var upsertItem: UpsertItem
    lateinit var noteRepository: FakeNoteRepoImpl


    @Before
    fun setup(){
        noteRepository = FakeNoteRepoImpl()
        upsertItem = UpsertItem(noteRepository)
    }

    @Test
    fun `upsert item with empty title, should retun false`() = runTest {
        val result = upsertItem.invoke(
            title = "",
            description = "description",
            image = "image"
        )

        assertFalse(result)
    }

    @Test
    fun `upsert item with empty description , should return false`() = runTest {
        val result = upsertItem.invoke(
            title = "title",
            description = "",
            image = "image"
        )

        assertFalse(result)
    }

    @Test
    fun `upsert item with valid title and description, should return true`() = runTest {
        val result = upsertItem.invoke(
            title = "title",
            description = "description",
            image = "image"
        )

        assertTrue(result)
    }
}