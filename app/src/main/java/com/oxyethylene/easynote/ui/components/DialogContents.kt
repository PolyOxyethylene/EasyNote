package com.oxyethylene.easynote.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.R

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.components
 * @ClassName    : DialogContents.java
 * @createTime   : 2024/2/23 18:04
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 对话框的自定义布局
 */
@Composable
fun PermissionDialog() {

    val context = LocalContext.current

    val permissionInfoList = context.resources.getStringArray(R.array.permission_request_list)

    Column (
        Modifier.padding(top = 10.dp, start = 30.dp, end = 30.dp, bottom = 20.dp).fillMaxSize()
    ) {

//        Text("为保证软件的正常运行, Easy Note 将会请求以下权限")

        permissionInfoList.forEach {
            info ->
            val i = info.indexOf('-')
            Text(info.substring(0, i), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            Text(info.substring(i+1), fontSize = 14.sp, color = Color.Gray)
        }

    }

}