package com.example.notes.core.presentation.composables

import org.junit.Assert.*


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.notes.core.presentation.MainActivity
import com.example.notes.core.presentation.di.Appmodule
import com.example.notes.core.presentation.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(Appmodule::class)
class AddNoteScreenTest{

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addNoteScreenEndToEndTest() {
        composeRule.onNodeWithTag(TestTags.ADD_NOTE_FAB)
            .performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("title")

        composeRule.onNodeWithTag(TestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("description")

        composeRule.onNodeWithTag(TestTags.NOTE_IMAGE + "")
            .performClick()

        composeRule.onNodeWithTag(TestTags.SEARCH_IMAGE_DIALOG)
            .assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.SEARCH_IMAGE_TEXT_FIELD)
            .performTextInput("query")

        runBlocking {
            delay(600)
        }

        composeRule.onNodeWithTag(TestTags.PICKED_IMAGE + "image3")
            .performClick()

        composeRule.onNodeWithTag(TestTags.NOTE_IMAGE + "image3")
            .assertIsDisplayed()

    }
}