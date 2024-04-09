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
 * @ClassName    : RecyclePageUI.java
 * @createTime   : 2024/4/9 22:08
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 回收站界面
 */
@Composable
fun RecyclePageUI () {

    Column (
        modifier = Modifier.fillMaxSize()
    ) {

        SimpleTitleBar("回收站")

        Box (
            Modifier.fillMaxSize()
        ) {

            Text(
                text = "功能开发中，敬请期待",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Center)
            )

        }

    }

}