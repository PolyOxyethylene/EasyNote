package com.oxyethylene.easynotedemo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynotedemo.ui.theme.GreyDarker
import com.oxyethylene.easynotedemo.ui.theme.GreyLighter

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
 */
@Composable
fun InputText (modifier: Modifier, inputText : MutableState<String>, hint : String) {

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
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.background(GreyLighter).padding(8.dp),
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
 *  文本编辑区域
 */
@Composable
fun TextArea (modifier: Modifier, inputText : State<String>, enable : Boolean, _onValueChange : (String) -> Unit) {

    BasicTextField(
        value = inputText.value,
        modifier = modifier,
        onValueChange = _onValueChange,
        enabled = enable,
        textStyle = TextStyle(fontSize = 14.sp, color = GreyDarker),
        decorationBox = { innerTextField ->
            Box {
                Surface(
                    modifier = modifier.fillMaxSize(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {

                        Box(modifier = modifier.padding(start = 4.dp, end = 4.dp)) {
                            innerTextField()
                        }

                    }
                }
            }
        }
    )
}
