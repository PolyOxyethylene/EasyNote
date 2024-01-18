package com.oxyethylene.easynotedemo.ui.editactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynotedemo.ui.components.SimpleTitleBar
import com.oxyethylene.easynotedemo.ui.components.TextArea
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.GreyDarker
import com.oxyethylene.easynotedemo.util.NoteUtil

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

/**
 *  编辑界面的 ui 界面
 *  @param viewModel 编辑活动的 viewModel
 */
@Composable
fun EditPageArea() {

    var input = rememberSaveable { mutableStateOf(NoteUtil.noteContent) }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(BackGround),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EditPageTopBar("编辑")

        TitleLine(NoteUtil.getTitle(), Modifier.padding(top = 10.dp))

        EditableArea(input)

    }

}

/**
 *  顶部导航栏
 *  @param title 顶部的标题
 *  @param modifier 设置导航栏外观
 */
@Composable
fun EditPageTopBar (title: String, modifier: Modifier = Modifier) = SimpleTitleBar(title, modifier)

/**
 *  文章的标题栏
 *  @param noteTitle 文章的标题
 *  @param modifier 定制组件外观
 *  @param viewModel
 */
@Composable
fun TitleLine(noteTitle: String, modifier: Modifier) {

    Column (
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = noteTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        Text(text = "目前只能打字和 emoji 真是抱歉", fontSize = 10.sp, color = GreyDarker, modifier = Modifier.padding(top = 10.dp))
    }

}

@Composable
fun EditableArea(input: MutableState<String>) {

    Column (modifier = Modifier.padding(20.dp).navigationBarsPadding()) {
        TextArea(
            Modifier,
            input,
            true,
            {
                input.value = it
                NoteUtil.noteContent = it
            }
        )
    }

}