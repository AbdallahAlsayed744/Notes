package com.example.notes.core.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.notes.core.data.local.DB_Dao
import com.example.notes.core.data.local.Mydatabase
import com.example.notes.core.data.local.model
import com.example.notes.core.presentation.di.Appmodule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton


@HiltAndroidTest
@SmallTest
@UninstallModules(Appmodule::class)
class NotedaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this) // Use Hilt's rule to inject Hilt components

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() // Set the main coroutines dispatcher for unit testing

    @Inject
    lateinit var mydatabase:Mydatabase

    private lateinit var notedao:DB_Dao

    @Before
    fun setup(){
        hiltRule.inject()
        notedao=mydatabase.NoteDao
    }

    @After
    fun teardown(){
        mydatabase.close()
    }

    @Test
    fun should_Return_Emptynotes_when_no_notes_created() = runTest {

        assertTrue(notedao.getAllNotes().isEmpty())


    }

    @Test
    fun should_Return_noteslist_when_notes_notempty() = runTest {

        for (i in 1..5){
          val mymodel=  model(
                title = "title $i",
                description = "description $i",
                imageUrl = "imageurl $i",
                dateAdded = System.currentTimeMillis()
            )

            notedao.upsertitem(mymodel)

        }

        assertTrue(notedao.getAllNotes().isNotEmpty())


    }

    @Test
    fun should_delete_noteitem_when_deteteitem_called()= runTest {


            val mmodel = model(
                title = "title ",
                description = "description ",
                imageUrl = "imageurl ",
                dateAdded = System.currentTimeMillis()
            )
            notedao.upsertitem(mmodel)


        notedao.deleteitem(mmodel)

        assertTrue(!notedao.getAllNotes().contains(mmodel))



    }



}