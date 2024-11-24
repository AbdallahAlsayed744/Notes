package com.example.notes.core.presentation.composables

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.core.presentation.screen


@Composable
fun screen(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = screen.NoteList){
        composable<screen.NoteList> {
            NoteListScreen(
                onNavigateToAddNote = {
                    navController.navigate(screen.AddNote)
                }
            )
        }

        composable<screen.AddNote> {
            AddNoteScreen(onSave = {
                navController.popBackStack()
            }
            )
        }


    }


}