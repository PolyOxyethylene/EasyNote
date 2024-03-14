package com.oxyethylene.easynote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.domain.Dentry
import com.oxyethylene.easynote.ui.mainactivity.SearchListItem
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.util.FileUtil

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
/**
 * 主页权限请求框
 */
@Composable
fun PermissionDialog() {

    val context = LocalContext.current

    val permissionInfoList = context.resources.getStringArray(R.array.permission_request_list)

    Column (
        Modifier.padding(start = 30.dp, end = 30.dp, bottom = 20.dp).fillMaxSize()
    ) {

        permissionInfoList.forEach {
            info ->
            val i = info.indexOf('-')
            Text(info.substring(0, i), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 10.dp))
            Text(info.substring(i+1), fontSize = 14.sp, color = Color.Gray)
        }

    }

}

/**
 * 主页搜索框
 */
@Composable
fun SearchBox () {

    val input = remember { mutableStateOf("") }

    var resultList by remember { mutableStateOf(ArrayList<Dentry>()) }

    val manager = LocalFocusManager.current

    val context = LocalContext.current

    Column (
        modifier = Modifier.navigationBarsPadding().fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        InputText(
            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp).fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            inputText = input,
            singleLine = false,
            hint = "搜索文件/事件..."
        ) {
            resultList = FileUtil.searchFileByName(input.value) as ArrayList<Dentry>
        }


        if (resultList.isEmpty()) {
            Text(
                text = "暂无搜索结果",
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 60.dp, bottom = 80.dp)
            )
        }

        Column (
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                .navigationBarsPadding()
                .fillMaxSize()
        ) {

            resultList.forEachIndexed {
                index, dentry ->
                SearchListItem(dentry, context) {
                    input.value = ""
                    resultList = ArrayList()
                    manager.clearFocus()
                }
                if (index < resultList.size - 1) {
                    Row(Modifier.fillMaxWidth().height(0.5.dp).background(GreyLighter)) {}
                }
            }

        }

    }

}