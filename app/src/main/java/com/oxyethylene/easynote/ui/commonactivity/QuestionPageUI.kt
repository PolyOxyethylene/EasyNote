package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.ui.components.SimpleTitleBar

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : QuestionPageUI.java
 * @createTime   : 2024/3/17 1:23
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 常见问题界面
 */
@Composable
fun QuestionPageUI () {

    Column {

        SimpleTitleBar("常见问题")

        Box (Modifier.fillMaxSize()) {

            Text(text = "我还没想好写啥 (。_。)", fontSize = 14.sp, color = Color.LightGray, modifier = Modifier.align(Alignment.Center))

        }

    }

}