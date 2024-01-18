package com.oxyethylene.easynotedemo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynotedemo.ui.theme.SkyBlue

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.ui
 * @ClassName    : CustomedComponents.java
 * @createTime   : 2023/12/18 22:49
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 *  自定义的文本输入框
 *  @param inputText 输入框的文本
 *  @param shape 输入框的形状
 *  @param hint 提示性信息
 *  @param modifier 设置输入框的长宽
 */
@Composable
fun InputText (modifier: Modifier, shape: Shape, containerColor: Color, inputText : MutableState<String>, hint : String) {

    BasicTextField(
        value = inputText.value,
        modifier = modifier,
        onValueChange = {
            inputText.value = it
        },
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp),
        decorationBox = { innerTextField ->
            Box {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = shape
                ) {
                    Row(
                        modifier = Modifier.background(containerColor).padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(modifier = Modifier.padding(start = 16.dp, end = 4.dp)) {
                            if (inputText.value.isEmpty()) Text(text = hint, color = Color(0x88000000), fontSize = 16.sp)
                            innerTextField()
                        }

                    }
                }
            }
        }
    )
}

/**
 *  文章内容编辑区域
 *  @param modifier 定制组件外观
 *  @param inputText 输入框的文本
 *  @param enable 是否支持输入，默认为 true
 *  @param _onValueChange 输入框内容更新时执行的方法
 */
@Composable
fun TextArea (modifier: Modifier, inputText : MutableState<String>, enable : Boolean = true, _onValueChange : (String) -> Unit) {

    BasicTextField(
        value = inputText.value,
        modifier = modifier,
        onValueChange = _onValueChange,
        enabled = enable,
        textStyle =
        TextStyle(
            fontSize = 16.sp,
            color = Color.DarkGray,
            lineHeight = 28.sp,
            textAlign = TextAlign.Justify,
            textIndent = TextIndent(firstLine = 32.sp),
        ),
        cursorBrush = SolidColor(SkyBlue),
        decorationBox = { innerTextField->
            Column (
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Column (
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        Spacer(Modifier.size(10.dp))
                        Box(modifier = Modifier.fillMaxSize().padding(top = 6.dp, bottom = 6.dp, start = 14.dp, end = 14.dp)) {
                            innerTextField()
                        }
                    }

                }
            }
        }
    )
}
