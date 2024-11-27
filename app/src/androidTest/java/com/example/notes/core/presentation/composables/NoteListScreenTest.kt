package com.example.notes.core.presentation.composables

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.notes.core.presentation.MainActivity
import com.example.notes.core.presentation.di.Appmodule
import com.example.notes.core.presentation.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(Appmodule::class)
class NoteListScreenTest {

  @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)
  @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

  @Before
    fun setUp() {
        hiltRule.inject()
    }



    @Test
    fun test_node_endtoendSreen(){
        insernode(1)
        note_is_displayed(1)
        insernode(2)
        note_is_displayed(2)
        insernode(3)
        insernode(4)
        insernode(5)
        insernode(6)

        deleteNode(4)
        deleteNode(5)
        deleteNode(6)
        note_is_not_displayed(4)
        note_is_not_displayed(5)
    }










  private fun insernode(nodeitem:Int){
      composeTestRule.onNodeWithTag(TestTags.ADD_NOTE_FAB)
          .performClick()
      composeTestRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
          .performTextInput("Title $nodeitem")
        composeTestRule.onNodeWithTag(TestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("Description $nodeitem")

        composeTestRule.onNodeWithTag(TestTags.SAVE_BUTTON)
            .performClick()
  }

  private fun deleteNode(nodeitem: Int){
      composeTestRule.onNodeWithContentDescription(TestTags.DELETE_NOTE + "Title $nodeitem")
          .performClick()

  }

  private fun note_is_displayed(nodeitem: Int){
      composeTestRule.onNodeWithText("Title $nodeitem")
          .assertIsDisplayed()
  }

  private fun note_is_not_displayed(nodeitem: Int){
      composeTestRule.onNodeWithText("Title $nodeitem")
          .assertIsNotDisplayed()
  }
}