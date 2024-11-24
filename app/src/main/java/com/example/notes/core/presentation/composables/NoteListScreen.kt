package com.example.notes.core.presentation.composables

import android.annotation.SuppressLint
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.notes.R
import com.example.notes.core.domain.model.noteitem
import com.example.notes.core.presentation.util.TestTags
import com.example.notes.core.presentation.viewmodel.NoteViewModel

@Composable
fun NoteListScreen(
    onNavigateToAddNote: () -> Unit,
    noteListViewModel: NoteViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        noteListViewModel.getAllnotes()
    }

    val ordernoteListState by noteListViewModel.orderstate.collectAsState()
    val allitems by noteListViewModel.notestatelist.collectAsState()




    NoteListBody(allitems =allitems , onclicktoadd =onNavigateToAddNote, boolean =ordernoteListState , onOrder = {
        noteListViewModel.OrderByDateorTime()
    })
    
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteListBody( noteListViewModel: NoteViewModel = hiltViewModel(),allitems:List<noteitem>,onclicktoadd:()->Unit,boolean: Boolean,onOrder:()->Unit) {

    val myallitems by noteListViewModel.notestatelist.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 26.dp, horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = stringResource(id = R.string.notes,allitems.size), fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Row (modifier = Modifier.clickable {
                    onOrder()
                }){


                    Text(text = if(boolean) stringResource(id = R.string.d) else stringResource(id = R.string.t), fontWeight = FontWeight.Bold, fontSize = 19.sp)
                    Spacer(modifier =Modifier.padding(4.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.Sort, contentDescription ="" )


                }


            }
        }, floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .testTag(TestTags.ADD_NOTE_FAB),
                onClick = {
                    onclicktoadd()

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_a_note)
                )
            }
        }

    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding()
                ),

        ) {

            items(allitems.size) {index->
                NoteListScreenContent(allitems[index], onDelete = {
                    noteListViewModel.deleteItem(myallitems[index])

                })

                Spacer(modifier =Modifier.height(10.dp))


            }

        }



    }

}

@Composable
fun NoteListScreenContent(
    Notintem:noteitem,
    onDelete: (Int) -> Unit
) {

    Card(

        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.purple_500)),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
       ) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)){
            AsyncImage(
                modifier = Modifier

                    .width(130.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Notintem.imageUrl)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = Notintem.title,
                contentScale = ContentScale.Crop
            )



            Column(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.8f)) {
                Text(text = Notintem.title, color = Color.White,fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = Notintem.description, modifier = Modifier.padding(top = 19.dp) ,color = Color.White,fontSize = 16.sp)

            }



            Icon(

                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding()
                    .clickable {
                        onDelete(Notintem.id)
                    },
                imageVector = Icons.Default.Clear,
                contentDescription = TestTags.DELETE_NOTE + Notintem.title,
                tint = MaterialTheme.colorScheme.onPrimary,
            )



        }


    }
}

//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun NoteListScreenPreview() {
//    NoteListBody(listOf(noteitem(
//        "dqelwlflkw",
//        "qwl;fl;qfqll;fq;lflq;fwvelwevllwevl",
//        "",
//        0
//    ),
//        noteitem(
//            "dqelwlflkw",
//            "qwl;fl;qfqll;fq;lflq;fwvelwevllwevl",
//            "",
//            0
//        ),
//        noteitem(
//            "dqelwlflkw",
//            "qwl;fl;qfqll;fq;lflq;fwvelwevllwevl",
//            "",
//            0
//        ),
//        noteitem(
//            "dqelwlflkw",
//            "qwl;fl;qfqll;fq;lflq;fwvelwevllwevl",
//            "",
//            0
//        ),
//        noteitem(
//            "dqelwlflkw",
//            "qwl;fl;qfqll;fq;lflq;fwvelwevllwevl",
//            "",
//            0
//        ),
//        noteitem(
//            "dqelwlflkw",
//            "qwl;fl;qfqll;fq;lflq;fwvelwevllwevl",
//            "",
//            0
//        )
//
//        ),{
//
//    }
//    )
//
//}

//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun NoteListScreenContentPreview() {
//    NoteListScreenContent(noteitem("title", "description", "",0))
//
//}


