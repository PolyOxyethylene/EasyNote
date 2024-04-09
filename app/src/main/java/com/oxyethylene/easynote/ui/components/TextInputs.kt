package com.oxyethylene.easynote.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.ui.theme.SkyBlue

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.ui
 * @ClassName    : CustomedComponents.java
 * @createTime   : 2023/12/18 22:49
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 *  自定义的文本输入框
 *  @param modifier 设置输入框的长宽和边距
 *  @param shape 输入框的形状
 *  @param containerColor 输入框的颜色，默认为白色
 *  @param inputText 输入框的文本
 *  @param fontSize 搜索框文本的字体大小，默认 16.sp
 *  @param hint 提示性信息，默认为空
 *  @param singleLine 是否为单行，默认为 true
 *  @param onTextChange 当输入文本发生改变时执行的额外操作，默认什么都不做
 */
@Composable
fun InputText (
    modifier: Modifier,
    shape: Shape,
    containerColor: Color = Color.White,
    inputText: MutableState<String>,
    hint : String = "",
    fontSize: TextUnit = 14.sp,
    singleLine: Boolean = true,
    onTextChange: () -> Unit = {})
{

    BasicTextField(
        value = inputText.value,
        modifier = modifier,
        onValueChange = {
            inputText.value = it
            onTextChange()
        },
        singleLine = singleLine,
        textStyle = TextStyle(fontSize = fontSize),
        decorationBox = { innerTextField ->
            Box {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = shape
                ) {
                    Box(
                        modifier = Modifier.background(containerColor).padding(8.dp),
                    ) {

                        Box(modifier = Modifier.padding(start = 16.dp, end = 40.dp).align(Alignment.CenterStart)) {
                            if (inputText.value.isEmpty()) Text(text = hint, color = Color(0x88000000), fontSize = fontSize)
                            innerTextField()
                        }

                        if (inputText.value.isNotEmpty()) {
                            Image(
                                painter = painterResource(R.mipmap.ic_clean_all),
                                contentDescription = "清空输入框",
                                modifier = Modifier.padding(end = 10.dp)
                                    .size(20.dp)
                                    .align(Alignment.CenterEnd)
                                    .clickable {
                                        inputText.value = ""
                                        onTextChange()
                                    }
                            )
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
