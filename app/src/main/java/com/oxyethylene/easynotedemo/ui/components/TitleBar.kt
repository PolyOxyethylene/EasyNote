package com.oxyethylene.easynotedemo.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.ui.components
 * @ClassName    : TitleBar.java
 * @createTime   : 2024/1/15 22:31
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 *  应用界面的标题栏
 *  @param modifier 定制外观
 *  @param leftContent 显示在左侧的内容，建议设置为某个图标或者某个按钮
 *  @param centerContent 显示在中间的内容，建议是一个标题
 *  @param rightContent 显示在右侧的内容，建议设置为某个图标或者某个按钮
 */
@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    centerContent: @Composable () -> Unit
) {

    Box(
        modifier = modifier.statusBarsPadding().fillMaxWidth().padding(top = 12.dp)
    ) {
        Row (Modifier.align(Alignment.CenterStart)) {
            leftContent()
        }
        Row (Modifier.align(Alignment.Center)) {
            centerContent()
        }
        Row (Modifier.align(Alignment.CenterEnd)) {
            rightContent()
        }
    }

}

/**
 *  简单的标题栏，只有返回上一级界面以及一个文字标题
 *  @param title 页面的文字标题
 *  @param modifier 定制外观
 */
@Composable
fun SimpleTitleBar (
    title: String,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    TitleBar(
        modifier = modifier,
        leftContent = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.DarkGray),
                onClick = {
                    (context as Activity).finish()
                }) {
                BackIcon(Modifier.size(20.dp).align(Alignment.CenterVertically))
            }
        },
        rightContent = {}
    ) {
        Text(text = title, color = Color.DarkGray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }

}