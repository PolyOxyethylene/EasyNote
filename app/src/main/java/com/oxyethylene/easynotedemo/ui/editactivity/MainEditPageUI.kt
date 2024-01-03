package com.oxyethylene.easynotedemo.ui.editactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.GreyDarker
import com.oxyethylene.easynotedemo.viewmodel.MainViewModel

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.ui
 * @ClassName    : MainEditPageUI.java
 * @createTime   : 2023/12/20 20:00
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */

@Composable
fun MainEditArea(viewModel: MainViewModel) {

    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxSize()
            .background(BackGround),
    ) {

        TitleLine(Modifier.padding(top = 10.dp), viewModel)

        Row(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 16.dp, bottom = 10.dp).fillMaxWidth().height(1.dp).background(
            GreyDarker).clip(RoundedCornerShape(1.dp))) {}

        EditableArea(viewModel)


    }

}

@Composable
fun TitleLine(_modifier: Modifier, viewModel: MainViewModel) {

//    val currentTitle by viewModel.currentNoteTitle.observeAsState(NoteDefaults.defaultTitle)
//
//    Text(currentTitle, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = _modifier)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditableArea(viewModel: MainViewModel) {

//    val currentContent = viewModel.currentNoteContent.observeAsState(NoteDefaults.defaultContent)

//    val writable by viewModel.writable.observeAsState(false)
//
//    TextArea(
//        Modifier.background(BackGround),
//        currentContent,
//        writable,
//        { viewModel.updateCurrentContent(it) }
//    )







}